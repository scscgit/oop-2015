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

/**
 * Fezzes are <b>cool</b>, too. I am a huge <b>fan</b>.
 * 
 * @author Steve
 */
public class Cooler extends AbstractAdvancedActor implements Switchable
{
    /************************************\
    |* Week 4 Cooler, act()
    \************************************/
    
    private Reactor reactor;
    private boolean on;
    
    private int animationRun;
    private int animationStop;
    
    //private static final sk.tuke.oop.framework.Animation stoppedFanAnimation = new sk.tuke.oop.framework.Animation("resources/images/fan.png", 32, 32, 200);
    
    public Cooler(Reactor reactor)
    {
	//Inicializacie premennych
	this.on = false;
	
	//Vstupy
	setReactor(reactor);
	
	//Animacie
	this.animationRun = addAnimation("fan",32,32,200);
	this.animationStop = addAnimation("fan",32,32,200,false,false);
	updateAnimation();
    }
    
    private void updateAnimation()
    {
	if(isOn())
	{
	    updateAnimation(this.animationRun);
	}
	else
	{
	    updateAnimation(this.animationStop);
	}
    }

    @Override
    public void turnOn()
    {
	this.on = true;
	updateAnimation();
    }
    
    @Override
    public void turnOff()
    {
	this.on = false;
	updateAnimation();
    }
    
    @Override
    public boolean isOn()
    {
	return this.on;
    }
    
    private void setReactor(Reactor reactor)
    {
	this.reactor = reactor;
    }
    
    public Reactor getReactor()
    {
	return this.reactor;
    }
    
    @Override
    public void act()
    {
	//Ak je chladic zapnuty, reaktor pripojeny a jeho teplota je >0, tak ho v kazdom cykle schladim o 1 stupen.
	if(isOn() && getReactor() != null && getReactor().getTemperature()>0)
	{
	    getReactor().decreaseTemperature(1);
	}
    }
}
