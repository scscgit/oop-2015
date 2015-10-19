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
 * Moderna technologia - bezpecny pristroj, ktory funguje nasledujucim principom:
 * Vstupujucu osobu najprv nascanuje, potom zabije, nasledne odosle data pomcoou UDP protokolu do cieloveho "Teleportu" a tam ho naklonuje.
 * 
 * Inak povedane, je to nieco ako vytah. Aj preto ma texturu s nazvom lift.png.
 * 
 * @author Steve
 */
public class Teleport extends AdvancedActor
{
    /************************************\
    |* Week 4 Doplnkove ulohy
    \************************************/
    
    private Teleport destinationTeleport;
    private boolean teleported;
    
    public Teleport(Teleport destinationTeleport)
    {
	//Inicializacie
	this.destinationTeleport = null;
	setDestinationTeleport(destinationTeleport);
	this.teleported=false;
	
	//Animacie
	addAnimation("lift", 48, 48, 100);
	updateAnimation();
    }
    
    private void updateAnimation()
    {
	updateAnimation("lift");
    }
    
    //Vnutorna funkcia triedy na osetrenie zakazanych stavov cieloveho teleportu
    private void setDestinationTeleport(Teleport destinationTeleport)
    {
	//Ak je vstupom existujuci Teleport, ktory nie je rovnaky ako Teleport, ktory nastavujeme, tak bude novym cielom.
	if(destinationTeleport!=null && destinationTeleport!=this)
	{
	    this.destinationTeleport = destinationTeleport;
	}
    }
    
    //Nastavenie noveho cieloveho teleportu
    public void setDestination(Teleport destinationTeleport)
    {
	setDestinationTeleport(destinationTeleport);
    }
    
    //Vytvorenie kvantoveho previazania castic sprostredkujucich prenos dat. Garantovana spolahlivost.
    public void intertwineWith(Teleport destinationTeleport)
    {
	if(destinationTeleport!=null)
	{
	    destinationTeleport.setDestination(this);
	    setDestination(destinationTeleport);
	}
    }
    
    public Teleport getDestination()
    {
	return this.destinationTeleport;
    }
    
    //Presunie hraca na suradnice stredu cieloveho Teleportu.
    public void teleportPlayer()
    {
	if(getDestination()!=null)
	{
	    getPlayer().setPosition(destinationTeleport.getX()+((this.getWidth()-getPlayer().getWidth())/2), destinationTeleport.getY()+((this.getHeight()-getPlayer().getHeight())/2));
	    
	    //Cielovy teleport sa deaktivuje az do doby, kym ho hrac neopusti
	    getDestination().teleported = true;
	    //Osetrenie pripadu, ked sa hrac zasekne medzi dvoma teleportami (odosielaci teleport sa tiez deaktivuje)
	    this.teleported = true;
	}
    }
    
    @Override
    public void act()
    {
	if(getDestination()!=null)
	{
	    //Ak sa hrac nachadza v Teleporte, tak sa presunie do cieloveho Teleportu, ktory sa deaktivuje az kym z neho nevystupi
	    if(this.teleported == false)
	    {
		if(getPlayer().intersects(this))
		{
		    teleportPlayer();
		}
	    }
	    //Ak sa v nom uz hrac nenachadza, tak sa dany Teleport odblokuje
	    else if(getPlayer().intersects(this)==false)
	    {
		this.teleported=false;
	    }
	}
    }
}
