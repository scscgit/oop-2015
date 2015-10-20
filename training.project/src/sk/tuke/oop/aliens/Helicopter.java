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
 * Apache helicopter on port 80, A.K.A flying HTTP server application.
 * 
 * @author Steve
 */
public class Helicopter extends AbstractAdvancedActor
{
    /************************************\
    |* Week 4 Doplnkove ulohy
    \************************************/
    
    private boolean seekAndDestroy;
    
    public Helicopter()
    {
	//Inicializacie
	seekAndDestroy = false;
	
	//Animacie
	addAnimation("heli", 64, 64, 100);
	updateAnimation();
    }
    
    private void updateAnimation()
    {
	updateAnimation("heli");
    }
    
    public void searchAndDestroy()
    {
	this.seekAndDestroy = true;
    }
    
    public void stopSearchAndDestroy()
    {
	this.seekAndDestroy = false;
    }
    
    public boolean isSearchAndDestroy()
    {
	return this.seekAndDestroy;
    }
    
    @Override
    public void act()
    {
	//Nahananie hraca rychlostou 1*
	if(seekAndDestroy)
	{
	    //X
	    if(getX()<getPlayer().getX())
	    {
		setPosition(getX()+1, getY());
	    }
	    else if(getX()>getPlayer().getX())
	    {
		setPosition(getX()-1, getY());
	    }
	    
	    //Y
	    if(getY()<getPlayer().getY())
	    {
		setPosition(getX(), getY()+1);
	    }
	    else if(getY()>getPlayer().getY())
	    {
		setPosition(getX(), getY()-1);
	    }
	}
	
	//Po naraze do hraca mu znizim hp. Ak ma hrac aspon 1 hp, tak mu dam 1 dmg
	if(getPlayer().intersects(this) && getPlayer().getEnergy()>0)
	{
	    getPlayer().setEnergy(getPlayer().getEnergy()-1);
	}
    }
}
