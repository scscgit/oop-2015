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

import java.util.List;
import java.util.ArrayList;

/**
 * Ide to vybuchnut.
 * 
 * @author Steve
 */
public class Reactor extends AdvancedActor implements Switchable, Repairable
{
    /************************************\
    |* Week 2, Reactor
    \************************************/
    
    //Konstanty
    public static final int MAX_TEMPERATURE = 5000;
    public static final int CRITICAL_TEMPERATURE = 2000;
    public static final int MIN_TEMPERATURE = 0;
    public static final int MIN_DAMAGE = 0;
    public static final int CRITICAL_DAMAGE = 50;
    public static final int MAX_DAMAGE = 100;
    //public static final int ZAPNUTE = 1;
    //public static final int VYPNUTE = 0;
    
    //Stavy
    private int temperature;
    private boolean temperature_zvysok;
    private int damage;
    private boolean on;
    
    //Informacne atributy
    private String manufacturer;
    private int yearOfProduction;
    
    //Objekty
    private List<EnergyConsumer> devices;
    
    public Reactor()
    {
	//Inicializacia
        this.temperature=MIN_TEMPERATURE;
        this.temperature_zvysok=false;
        this.damage=MIN_DAMAGE;
        this.on=false;
        this.manufacturer="";
        this.yearOfProduction=1;
        
	//Objekty
	this.devices = new ArrayList</*EnergyConsumer*/>();
	
	//Animacie
	addAnimation("reactor",80,80,100);
        addAnimation("reactor_on",80,80,100);
        addAnimation("reactor_hot", 80, 80, 50);
        addAnimation("reactor_broken", 80, 80, 100);
        updateAnimation();
    }
    
    public Reactor(String manufacturer, int yearOfProduction) throws ReactorException
    {
        this();
        
        //Priradenie novych parametrov
        this.manufacturer = manufacturer;
        setYearOfProduction(yearOfProduction);
    }
    
    class ReactorException extends Exception
    {
        public static final String yearOfProduction_below_or_zero = "Error. Reactor cannot be born when Christ was, or before that.";

        public ReactorException(String msg)
        {
            super(msg);
        }
    }
    
    //Automaticka volba spravnej animacie
    private void updateAnimation()
    {
        //Zniceny reaktor svoju animaciu nezmeni
        if(getDamage()==MAX_DAMAGE)
        {
            updateAnimation("reactor_broken");
        }
        else if(isOn())
        {
            if(getTemperature()>=4000/* && getDamage()<MAX_DAMAGE*/)
            {
                updateAnimation("reactor_hot");
                updateAnimationSpeed();
            }
            else if(getTemperature()<4000/* && getDamage()<MAX_DAMAGE*/)
            {
                updateAnimation("reactor_on");
                updateAnimationSpeed();
            }
        }
        else
        {
            updateAnimation("reactor");
        }
    }
    
    private void setYearOfProduction(int year) throws ReactorException
    {
        if(year<=0)
        {
            throw new ReactorException(ReactorException.yearOfProduction_below_or_zero);
        }
        this.yearOfProduction=year;
    }
    
    public int getYearOfProduction()
    {
        return this.yearOfProduction;
    }
    
    //Nastavi novu rychlost aktualnej animacie
    private void updateAnimationSpeed()
    {
        if(getDamage()>CRITICAL_DAMAGE && getDamage()<MAX_DAMAGE)
        {
            setAnimationSpeed(MAX_DAMAGE-getDamage());
        }
        else
        {
            setAnimationSpeed(getAnimationSpeed());
        }
    }
    
    public int getDamage()
    {
        return this.damage;
    }
    
    private void setDamage(int damage)
    {
        if(damage>MAX_DAMAGE)
        {
            this.damage=MAX_DAMAGE;
        }
        else if(damage<MIN_DAMAGE)
        {
            this.damage = MIN_DAMAGE;
        }
        else
        {
            this.damage = damage;
        }
	updateAnimation();
	updateElectricity();
    }
    
    public int getTemperature()
    {
        return this.temperature;
    }
    
    public void increaseTemperature(int inc)
    {
        //Ak nie je vstupny parameter zaporny
        if(inc>0)
        {
	    //Zvysi teplotu o vstup*modifier pri zohladneni vsetkych podmienok
            for(int i=0;i<inc;i++)
            {
		//poskodenie nad CRITICAL_DAMAGE% je 2x teplota
		if(getDamage()<=CRITICAL_DAMAGE)
		{
		    //Rychlost 1x
		    setTemperature(getTemperature()+1);
		}
		else
		{
		    //Rychlost 2x
		    setTemperature(getTemperature()+2);
		}
            }
        }
    }
    
    public void decreaseTemperature(int inc)
    {
        //Ak nie je vstupny parameter zaporny
        if(inc>0)
        {
	    //Ak este reaktor funguje, znizi teplotu o vstup*modifier pri zohladneni vsetkych podmienok
            for(int i=0;i<inc;i++)
            {
		if(getDamage()<MAX_DAMAGE && getDamage()>=CRITICAL_DAMAGE)
		{
		    //Rychlost znizovania teploty nad CRITICAL_DAMAGE% je 0.5x
		    if(this.temperature_zvysok)
		    {
			setTemperature(getTemperature()-1);
			this.temperature_zvysok=false;
		    }
		    else
		    {
			this.temperature_zvysok=true;
		    }
		}
		else if(getDamage()<MAX_DAMAGE)
		{
		    //Rychlost znizovania teploty pod CRITICAL_DAMAGE% je 1x
		    setTemperature(getTemperature()-1);
		}
            }
        }
    }
    
    //Funkcia urcena na vnutorne rutiny triedy, ktore menia teplotu na konkretnu hodnotu
    //Jej hlavnou ulohou je znicit reaktor
    private void setTemperature(int temperature)
    {
	//Nastavenie novej teploty
	if(temperature>=MAX_TEMPERATURE)
	{
	    //Ak je teplota vacsia nez maximalna, tak bude maximalna
	    this.temperature=MAX_TEMPERATURE;
	}
	else if(temperature<=MIN_TEMPERATURE)
	{
	    //Ak je teplota mensia nez minimalna, tak bude minimalna
	    this.temperature=MIN_TEMPERATURE;
	}
	else
	{
	    //Kym je teplota medzi maximalou a minimalnou, tak je platna
	    this.temperature=temperature;
	}
        
        //Aktualizovanie damage podla aktualnej teploty
        if(getTemperature()>CRITICAL_TEMPERATURE)
        {
            int current_damage = (getTemperature()-CRITICAL_TEMPERATURE)/30;
            if(getDamage()<current_damage)
            {
                setDamage(current_damage);
            }
        }
        
        //Operacie, ktore je nutne vykonat po kazdej zmene teploty
        if(getDamage()>=MAX_DAMAGE)
        {
            //Reaktor sa z bezpecnostnych dovodov po vybuchu vypne
            turnOff();
        }
        updateAnimation();
    }
    
    public boolean isServiceNeeded()
    {
	return getTemperature()>3000 && getDamage()>50;
    }
    
    /************************************\
    |* Week 3 Hammer, Controller funkcie
    \************************************/
    
    @Override
    public void turnOn()
    {
        this.on=true;
        updateAnimation();
	updateElectricity();
    }
    
    @Override
    public void turnOff()
    {
        this.on=false;
        updateAnimation();
	updateElectricity();
    }
    
    @Override
    public boolean isOn()
    {
        return this.on;
    }
    
    private void decreaseDamage(int damage)
    {
        if(damage>0)
        {
            //Znizi damage
            setDamage(getDamage()-damage);
            
            //Zisti, na aku maximalnu teplotu by sa zvysil reaktor v pripade hodnoty damage zmensenej o CRITICAL_DAMAGE
            int new_temperature = 30*(getDamage()-CRITICAL_DAMAGE)+CRITICAL_TEMPERATURE;
            
            //Podla potreby zmensi teplotu na maximalnu dosiahnutelnu pri danej hodnote damage zmensenej o CRITICAL_DAMAGE
            if(getTemperature()>new_temperature)
            {
                setTemperature(new_temperature);
            }
        }
    }
    
    @Override
    public void repair(AbstractTool hammer)
    {
        //Ak bolo pouzite kladivo
        if(hammer != null && Hammer.class.isInstance(hammer))
        {
	    //Ak sa kladivo uspesne pouzije
	    if(hammer.use())
	    {
		//Znizi hodnotu damage o 50
		decreaseDamage(50);
	    }
	}
    }
    
    //Funkcia na aktualizovanie stavu dodavky elektriny pripojenych zariadeni k reaktoru.
    //Je potrebne zavolat ju stale po zmene stavu zapnutia reaktora.
    private void updateElectricity()
    {
	//Ak je pripojene zariadenie, zapnem mu dodavku elektriny v pripade, ze je reaktor zapnuty a nie je pokazeny.
	for(EnergyConsumer device: this.devices)
	{
	    device.setElectricityFlow(isOn()&&(getDamage()<100));
        }
    }
    
    /************************************\
    |* Week 4 Cooler, act()
    \************************************/
    
    //Samovolne prehrievanie reaktora
    @Override
    public void act()
    {
	if(isOn() && (getDamage()<MAX_DAMAGE))
	{
	    increaseTemperature(1);
	}
    }
    
    /************************************\
    |* Week 5 Switchable
    \************************************/
    
    //Pripojenie zariadenia odoberajuceho elektrinu na reaktor
    //Definovana cinnost pri vstupe null: nezmenit nic
    public void addDevice(EnergyConsumer device)
    {
	//V pripade poziadavky na pripojenie svetla
        if(device!=null)
	{
	    //Pripojim nove zariadenie a aktualizujem ho
	    if(!this.devices.contains(device))
	    {
		this.devices.add(device);
		updateElectricity();
	    }
	    
	    /*
	    Stara verzia:
	    Ak uz bolo pripojene ine svetlo, najprv ho odpojim.
	    if(this.device!=null)
	    {
		removeDevice();
	    }
            this.device = device;
	    */
        }
    }
    
    public void removeDevice(EnergyConsumer device)
    {
	//Ak je pripojene zariadenie, odpojim mu dodavku elektriny a odpojim ho.
	if(device!=null)
        {
	    if(this.devices.contains(device))
	    {
		device.setElectricityFlow(false);
		this.devices.remove(device);
	    }
	}
	
	/*
	Stara verzia:
	if(this.device!=null)
        {
	    device.setElectricityFlow(false);
            this.device = null;
        }
	*/
    }
}
