/**
 * AnyServer -- Praktikum Experimentierkasten -- ADS
 *
 * @author E. Mumprecht
 * @version 1.0 -- Geruest fuer irgendeinen Server
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package ch.zhaw.ads;

public class AnyServer implements CommandExecutor {

    //----- Dies implementiert das CommandExecutor Interface.
    @Override
    public String execute(String command) {
        StringBuffer result = new StringBuffer(100);
        result.append("Die Eingabe war \"");
        result.append(command);
        result.append("\"\n");
        return result.toString();
    }
}//AnyServer   

