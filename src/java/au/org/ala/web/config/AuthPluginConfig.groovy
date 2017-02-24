package au.org.ala.web.config

import au.org.ala.userdetails.UserDetailsClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import groovy.transform.CompileStatic
import okhttp3.OkHttpClient
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import static java.util.concurrent.TimeUnit.MILLISECONDS

@CompileStatic
@Configuration("alaAuthPluginConfiguration")
class AuthPluginConfig {

    @Bean(name = ["defaultUserDetailsHttpClient", "userDetailsHttpClient"])
    OkHttpClient userDetailsHttpClient(GrailsApplication grailsApplication) {
        Integer readTimeout = (grailsApplication.config['userdetails']['baseUrl'] ?: '60000') as Integer
        new OkHttpClient.Builder().readTimeout(readTimeout, MILLISECONDS).build()
    }

    @Bean(name = ["defaultUserDetailsMoshi", "userDetailsMoshi"])
    Moshi userDetailsMoshi() {
        new Moshi.Builder().add(Date, new Rfc3339DateJsonAdapter()).build()
    }


    @Bean(name = "userDetailsClient")
    UserDetailsClient userDetailsClient(@Qualifier("userDetailsHttpClient") OkHttpClient userDetailsHttpClient,
                                        @Qualifier('userDetailsMoshi') Moshi moshi,
                                        GrailsApplication grailsApplication) {
        String baseUrl = grailsApplication.config["userdetails"]["baseUrl"] ?: 'https://auth.ala.org.au/userdetails/'
        new UserDetailsClient.Builder(userDetailsHttpClient, baseUrl).moshi(moshi).build()
    }

}