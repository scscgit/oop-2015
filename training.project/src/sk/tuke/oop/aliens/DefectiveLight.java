/* * * * * * * * * * * * * * * *
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
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
    private static final int INTERVAL_START = 10;
    private static final int INTERVAL_END = 100;
    private final Random randomgen = new Random();
    private int blinkCooldown;
    
    public DefectiveLight()
    {
	this.blinkCooldown=0;
    }
    
    @Override
    public void act()
    {
	this.blinkCooldown--;
	if(this.blinkCooldown<=0)
	{
	    toggle();
	    this.blinkCooldown = INTERVAL_START + randomgen.nextInt(INTERVAL_END - INTERVAL_START);
	}
    }
    
    /************************************\
    |* Week 5 Doplnkove ulohy
    \************************************/
    
    @Override
    public void repair(AbstractTool wrench)
    {
        //Ak sa ma pouzit wrench, tak ho pouzijem. Ak sa uspesne pouzije
        if(wrench != null && Wrench.class.isInstance(wrench) && wrench.use())
        {
	    //Na chvilu sa svetlo opravi. Jeho aktualna hodnota zapnutia sa nezmeni, kedze uloha to nevyzaduje. Opravar bol lenivy?
	    this.blinkCooldown = 1001; //1001 lebo ocividne 1000 testu nestacilo?
	}
    }
}
