/**
 * CommandExecutor -- Praktikum Experimentierkasten -- SW3 Dieses Interface muss
 * von jedem Server implementiert werden.
 *
 * @author E. Mumprecht
 * @version 1.0 -- Geruest fuer irgendeinen Server
 * @version 1.1 -- K. Rege Fehlerueckgabe hinzugefuegt
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

public interface CommandExecutor {

    /**
     * execute -- nimmt eine Kommandozeile, tut irgendetwas gescheites, und
     * berichtet das Resultat.
     *
     * @param command Kommandozeile
     * @return Resultat, ueblicherweise eine oder mehrere Zeilen.
     */
	 String execute(String command) throws Exception;
}//interface CommandExecutor