package io.github.john.tuesday.plugins.maven.publish.assist.model

import io.github.john.tuesday.plugins.MavenPublishAssistPlugin
import io.github.john.tuesday.plugins.helper.ExperimentalProviderWithErrorMessageApi
import io.github.john.tuesday.plugins.helper.propertyOrEnvironment
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider


/**
 * Configure targets to be excluded or included in [MavenPublishAssistPlugin]
 */
public abstract class FilterTargetExtension {
    /**
     * Exclude targets with a matching name
     */
    public abstract val exclude: Property<Regex>

    /**
     * Include targets with a matching name
     */
    public abstract val include: Property<Regex>

    /**
     * Returns a [Provider] which returns if [target] passes the filters and should be allowed
     */
    public fun shouldRun(target: Provider<String>): Provider<Boolean> {
        val includeMatch = include.zip(target) { regex, name -> regex.matches(name) }.orElse(Default.INCLUDE_RESULT)
        val excludeMatch = exclude.zip(target) { regex, name -> regex.matches(name) }.orElse(Default.EXCLUDE_RESULT)
        return includeMatch.zip(excludeMatch) { includeResult, excludeResult -> includeResult && !excludeResult }
    }

    /**
     * Initialize extension
     *
     * Set [include] and [exclude] convention using properties visible to [project]
     */
    @OptIn(ExperimentalProviderWithErrorMessageApi::class)
    internal fun init(project: Project) {
        val ignoreCase = project.propertyOrEnvironment(
            propertyKey = Properties.IgnoreCase.PROPERTY,
            environmentKey = Properties.IgnoreCase.ENVIRONMENT,
        )
            .map { Properties.IgnoreCase.IgnoreCaseValues.valueOfOrNull(it) ?: Properties.IgnoreCase.DEFAULT }
            .map {
                when (it) {
                    Properties.IgnoreCase.IgnoreCaseValues.False -> setOf()
                    Properties.IgnoreCase.IgnoreCaseValues.True -> setOf(RegexOption.IGNORE_CASE)
                }
            }

        include.convention(
            project.propertyOrEnvironment(
                propertyKey = Properties.Include.PROPERTY,
                environmentKey = Properties.Include.ENVIRONMENT,
            ).zip(ignoreCase) { pattern, options -> Regex(pattern, options) }
        )
        exclude.convention(
            project.propertyOrEnvironment(
                propertyKey = Properties.Exclude.PROPERTY,
                environmentKey = Properties.Exclude.ENVIRONMENT,
            ).zip(ignoreCase) { pattern, options -> Regex(pattern, options) }
        )
    }

    internal data object Default {
        const val NAME: String = "filterTarget"

        const val INCLUDE_RESULT: Boolean = true
        const val EXCLUDE_RESULT: Boolean = false
    }

    internal data object Properties {
        public data object Exclude {
            public const val PROPERTY: String = "targetFilter.exclude"
            public const val ENVIRONMENT: String = "TARGET_FILTER_EXCLUDE"
        }

        public data object Include {
            public const val PROPERTY: String = "targetFilter.include"
            public const val ENVIRONMENT: String = "TARGET_FILTER_INCLUDE"
        }

        public data object IgnoreCase {
            public const val PROPERTY: String = "targetFilter.ignoreCase"
            public const val ENVIRONMENT: String = "TARGET_FILTER_IGNORE_CASE"
            public val DEFAULT: IgnoreCaseValues = IgnoreCaseValues.True

            public enum class IgnoreCaseValues {
                True, False,
                ;

                public val value: String
                    get() = name.lowercase()

                public companion object {
                    public fun valueOfOrNull(name: String): IgnoreCaseValues? = entries.firstOrNull {
                        it.value == name.lowercase()
                    }
                }
            }
        }
    }
}