package net.allmarsh.awshelpers.cli.converters;


import com.amazonaws.regions.Regions;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;
import java.util.StringJoiner;


/**
 * Converts a String to a S3Resource object with
 */
public class StringToRegionConverter implements IStringConverter<Regions> {
    @Override
    public Regions convert(final String value) {
        try {
            return Regions.fromName(value.toLowerCase());
        } catch (final IllegalArgumentException e) {
            final StringJoiner joiner = new StringJoiner(", ");
            Arrays.stream(Regions.values())
                    .map(Regions::getName)
                    .forEachOrdered(joiner::add);
            throw new ParameterException(String.format("%s is not a valid region. Options: [%s]", value, joiner.toString()));
        }
    }
}