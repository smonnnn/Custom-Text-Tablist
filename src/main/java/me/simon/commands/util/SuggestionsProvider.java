package me.simon.commands.util;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.simon.Main;
import me.simon.config.Config;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class SuggestionsProvider {
    public static SuggestionProvider<ServerCommandSource> headerFooterSuggestions() {
        return (ctx, builder) -> getSuggestionsBuilder(builder, Arrays.asList(Config.INSTANCE.footer, Config.INSTANCE.header));
    }

    public static CompletableFuture<Suggestions> getSuggestionsBuilder(SuggestionsBuilder builder, List<String> list) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);

        if(list.isEmpty()) { // If the list is empty then return no suggestions
            return Suggestions.empty(); // No suggestions
        }

        for (String str : list) { // Iterate through the supplied list
            if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(str); // Add every single entry to suggestions list.
            }
        }
        return builder.buildFuture(); // Create the CompletableFuture containing all the suggestions
    }
}
