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
 * Apache helicopter on port 80, A.K.A flying HTTP server application.
 * 
 * @author Steve
 */
public class Helicopter extends AdvancedActor
{
    /************************************\
    |* Week 4 Doplnkove ulohy
    \************************************/
    
    private boolean seek_and_destroy;
    
    public Helicopter()
    {
	//Inicializacie
	seek_and_destroy = false;
	
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
	this.seek_and_destroy = true;
    }
    
    public void stopSearchAndDestroy()
    {
	this.seek_and_destroy = false;
    }
    
    public boolean isSearchAndDestroy()
    {
	return this.seek_and_destroy;
    }
    
    @Override
    public void act()
    {
	//Nahananie hraca rychlostou 1*
	if(seek_and_destroy)
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
	
	//Po naraze do hraca mu znizim hp
	if(getPlayer().intersects(this))
	{
	    //Ak ma hrac aspon 1 hp, tak mu dam 1 dmg
	    if(getPlayer().getEnergy()>0)
	    {
		getPlayer().setEnergy(getPlayer().getEnergy()-1);
	    }
	}
    }
}
