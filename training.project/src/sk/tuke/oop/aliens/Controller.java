/* * * * * * * * * * * * * * * *
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
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

/**
 * Microcontroller programovany v jazyku VHDL, ktory iba priradzuje logicku hodnotu 0 alebo 1 na vstup reaktora.
 * 
 * @author Steve
 */
public class Controller extends AbstractAdvancedActor
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
