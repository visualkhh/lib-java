/*
 * Copyright 2002-2015 Drew Noakes
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * More information about this project is available at:
 *
 *    https://drewnoakes.com/code/exif/
 *    https://github.com/drewnoakes/metadata-extractor
 */


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A sample application that creates HTML that shows image positions on a Google Map according to the GPS data
 * contained within each {@link GpsDirectory}, if available.
 */
public class photo
{
    public static void main(String args[]) throws ImageProcessingException, IOException
    {
    	File file = new File("C:\\temp\\1460728_1141_1453279518231.jpg");
        // Read all metadata from the image
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        // See whether it has GPS data
        Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
        for (GpsDirectory gpsDirectory : gpsDirectories) {
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            System.out.println(geoLocation+"-----");
        }
            
            
    }

}
