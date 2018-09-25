package com.andre601.suggesto.utils.config;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
 * |------------------------------------------------------------------|
 *   Code from Gary (GitHub: https://github.com/help-chat/Gary)
 *
 *   Used with permission of PiggyPiglet
 *   Original Copyright (c) PiggyPiglet 2018 (https://piggypiglet.me)
 * |------------------------------------------------------------------|
 */

public class ConfigUtil {

    public boolean exportResource(InputStream source, String destination){
        boolean success = true;

        try{
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception ex){
            success = false;
        }
        return success;
    }
}
