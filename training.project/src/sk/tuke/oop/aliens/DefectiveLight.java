/* * * * * * * * * * * * * * * *
 * Zadanie na predmet Objektové Programovanie
 *
 * Štefan Ciberaj, ZS 2015/2016
 * Technická univerzita v Košiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Voľný softvér, Open-Source GNU GPL v3+
 * Všeobecná verejná licencia. Program je dovolené voľne šíriť a upravovať.
 * Upravený program / časť programu môže ktokoľvek využiť ako na osobné,
 * tak aj komerčné účely, ale nemôže ho vydať s vlastným copyrightom,
 * ktorý nie je kompatibilný s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sk.tuke.oop.aliens;

import java.util.Random;

/**
 * LED dioda na integrovanom obvode programovana pomocou Arduina, ktora sa v (ne)pravidelnych intervaloch vypina a zapina.
 * 
 * @author Steve
 */
public class DefectiveLight extends Light implements Repairable
{
    /************************************\
    |* Week 4 Cooler, act()
    \************************************/
    
    //Rozpatie intervalu, medzi ktorym sa nahodne zmeni stav zapnutia/vypnutia svetla
    private static final int BLINK_INTERVAL_START = 10;
    private static final int BLINK_INTERVAL_END = 100;
    private final Random randomgen = new Random();
    private int blink_cooldown;
    
    public DefectiveLight()
    {
	this.blink_cooldown=0;
    }
    
    @Override
    public void act()
    {
	this.blink_cooldown--;
	if(this.blink_cooldown<=0)
	{
	    toggle();
	    this.blink_cooldown = BLINK_INTERVAL_START + randomgen.nextInt(BLINK_INTERVAL_END - BLINK_INTERVAL_START);
	}
    }
    
    /************************************\
    |* Week 5 Doplnkove ulohy
    \************************************/
    
    @Override
    public void repair(AbstractTool wrench)
    {
        //Ak bol pouzity wrench
        if(wrench != null && Wrench.class.isInstance(wrench))
        {
	    //Ak sa wrench uspesne pouzije
	    if(wrench.use())
	    {
		//Na chvilu sa svetlo opravi. Jeho aktualna hodnota zapnutia sa nezmeni, kedze uloha to nevyzaduje. Opravar bol lenivy?
		this.blink_cooldown = 1000;
	    }
	}
    }
}
