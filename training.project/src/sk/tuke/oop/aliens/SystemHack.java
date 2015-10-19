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

//import java.util.Random;
import sk.tuke.oop.framework.Input;

/**
 * Stack Overflow.
 * 
 * @author Steve
 */
public class SystemHack extends Computer
{
    //protected sk.tuke.oop.framework.Animation hacked_computer = new sk.tuke.oop.framework.Animation("resources/images/computer.png",20,24,200);
    
    //Premenne
    //boolean controller_state;
    
    //Deklaracie objektov
    private Reactor reactor;
    private Teleport teleport1;
    private Teleport teleport2;
    //Computer.ExactComputer pocitadlo;
    
    private DefectiveLight light;
    private Helicopter helicopter;
    private SmartCooler cooler;
    private TimeBomb bomb;
    
    private Controller controller;
    
    public SystemHack()
    {
	//Animacia a inicializacia
        
        updateAnimation();
        //randomgen = new Random();
	
	//Premenne
	//controller_state=false;
	
	//Objekty
	this.light = new DefectiveLight();
	this.reactor = new Reactor();
	this.reactor.addDevice(light);
	this.controller = new Controller(reactor);
	this.helicopter = new Helicopter();
	
	this.cooler = new SmartCooler(reactor);
	
	this.teleport1 = new Teleport(null);
	this.teleport2 = new Teleport(null);
	
	//pocitadlo = new Computer.ExactComputer();
    }
    
    private void updateAnimation()
    {
        //setAnimation(hacked_computer);
    }
    
    private boolean key(Input.Key k)
    {
	return sk.tuke.oop.framework.Input.getInstance().isKeyPressed(k);
    }
    
    private void add(sk.tuke.oop.aliens.actor.AbstractActor a)
    {
	if(a.getWorld()==null)
	{
	    a.setPosition(getPlayer().getX(), getPlayer().getY());
	    getWorld().addActor(a);
	}
    }
    
    @Override
    public String toString()
    {
	return "Hacked";
    }
    
    @Override
    public void act()
    {
	if(key(Input.Key.Q))
	{
	    
	}
	
	if(key(Input.Key.W))
	{
	    add(this.cooler);
	}
	
	if(key(Input.Key.E))
	{
	    add(this.helicopter);
	    if(this.helicopter.isSearchAndDestroy())
	    {
		this.helicopter.stopSearchAndDestroy();
	    }
	    else
	    {
		this.helicopter.searchAndDestroy();
	    }
	}
	
	if(key(Input.Key.R))
	{
	    add(this.reactor);
	    add(this.controller);
	    this.controller.toggle();
	}
	
	if(key(Input.Key.T))
	{
	    add(this.teleport1);
	}
	
	if(key(Input.Key.Z))
	{
	    add(this.teleport2);
	    this.teleport1.intertwineWith(this.teleport2);
	}
	
	if(key(Input.Key.U))
	{
	    
	}
	
	if(key(Input.Key.I))
	{
	    
	}
	
	if(key(Input.Key.O))
	{
	    
	}
	
	if(key(Input.Key.P))
	{
	    //add(pocitadlo);
	}
	
	if(key(Input.Key.L))
	{
	    add(this.light);
	    this.light.toggle();
	}
	
	if(key(Input.Key.B))
	{
	    this.bomb = new TimeBomb(100);
	    this.bomb.activate();
	    add(this.bomb);
	}
    }
}
