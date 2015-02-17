grails.servlet.version = "2.5"
//grails.project.class.dir = "target/classes"
//grails.project.test.class.dir = "target/test-classes"
//grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    //legacyResolve true // if using Grails > 2.2
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        mavenLocal()
        mavenRepo ("http://nexus.ala.org.au/content/groups/public/") {
            updatePolicy 'always'
        }
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.18'
        compile ('au.org.ala:ala-cas-client:2.1-SNAPSHOT') {
            // Ivy only
            transitive = false
            // Maven only
            excludes 'servlet-api'
        }
        compile ('org.jasig.cas.client:cas-client-core:3.1.12') {
            excludes 'servlet-api'
        }
    }

    plugins {
        compile(":tomcat:7.0.55",
                ":release:3.0.1",
                ":rest-client-builder:2.0.3") {
            export = false
        }
        compile ":rest:0.8"
    }
}

