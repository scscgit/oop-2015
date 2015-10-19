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

/**
 * Microcontroller programovany v jazyku VHDL, ktory iba priradzuje logicku hodnotu 0 alebo 1 na vstup reaktora.
 * 
 * @author Steve
 */
public class Controller extends AdvancedActor
{
    /************************************\
    |* Week 3 Hammer, Controller funkcie
    \************************************/
    
    //private boolean state;
    private final Reactor reactor;
    
    public Controller(Reactor reactor)
    {
        addAnimation("switch",16,16,50);
        updateAnimation();
        
        this.reactor = reactor;
        //state=reactor.isOn();
    }
    
    private void updateAnimation()
    {
        updateAnimation("switch");
    }
    
    public void turnOn()
    {
	if(this.reactor!=null)
	    this.reactor.turnOn();
    }
    
    public void turnOff()
    {
	if(this.reactor!=null)
	    this.reactor.turnOff();
    }
    
    public void toggle()
    {
	if(this.reactor!=null)
	{
	    if (!reactor.isOn())
	    {
		turnOn();
	    }
	    else
	    {
		turnOff();
	    }
	}
    }
}
