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
 * Svetlo svieti, vsetko ide.
 * 
 * @author Steve
 */
public class Light extends AdvancedActor implements Switchable, EnergyConsumer
{
    /************************************\
    |* Week 3 Hammer, Controller funkcie
    \************************************/
    
    private boolean on;
    private boolean electricity;
    
    public Light()
    {
        addAnimation("light_on",16,16,10);
        addAnimation("light_off",16,16,10);
        updateAnimation();
    }
    
    //Automaticka volba spravnej animacie
    private void updateAnimation()
    {
        if(this.on && this.electricity)
        {
            updateAnimation("light_on");
        }
        else
        {
            updateAnimation("light_off");
        }
    }
    
    @Override
    public void turnOn()
    {
        this.on=true;
        updateAnimation();
    }
    
    @Override
    public void turnOff()
    {        
        this.on=false;
        updateAnimation();
    }
    
    @Override
    public boolean isOn()
    {
	return this.on;
    }
    
    public void toggle()
    {
        if(!isOn())
        {
            turnOn();
        }
        else
        {
            turnOff();
        }
    }
    
    public boolean isPowered()
    {
        return this.electricity;
    }
    
    @Override
    public void setElectricityFlow(boolean set)
    {
        this.electricity = set;
        updateAnimation();
    }
}
