package net.nemo.whatever.config;

import net.nemo.whatever.security.ShiroUserIdSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.sql.DataSource;

/**
 * Created by tonyshi on 2016/12/27.
 */
@Configuration
@EnableSocial
@ComponentScan(basePackages = "net.nemo.whatever")
@PropertySource("classpath:props/social.properties")
public class SocialConfig implements SocialConfigurer{

    @Autowired
    private DataSource dataSource;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment env) {
        cfc.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.appId"), env.getProperty("facebook.appSecret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new ShiroUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(this.dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }
}
