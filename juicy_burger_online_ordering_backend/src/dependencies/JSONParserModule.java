package dependencies;

import dagger.Module;
import dagger.Provides;

import org.json.simple.parser.JSONParser;

import javax.inject.Singleton;

/**
 * JSONParserModule defines the Dagger module for a JSONParser Object
 */
@Module
public class JSONParserModule {

    /**
     * Dagger module to provide a JSONParser Object
     * @return JSONParser the parser to be returned
     */
    @Singleton
    @Provides
    public JSONParser JSONParseProvider() {
        return new JSONParser();
    }
}
