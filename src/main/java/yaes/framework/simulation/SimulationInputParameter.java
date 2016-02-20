package yaes.framework.simulation;

import java.io.Serializable;

public class SimulationInputParameter implements Serializable {
	public enum SimulationInputType {
		T_BYTE_ARRAY, T_DOUBLE, T_ENUM, T_INT, T_STRING
	}

	private static final long serialVersionUID = 10L;;

	byte[] bytevalue;
	double dvalue;
	@SuppressWarnings("rawtypes")
	Enum enumvalue;
	int ivalue;
	String name;
	String svalue;
	SimulationInputType type;

	/**
	 * Creates a simulation input parameter of type enum
	 * 
	 * @param ivalue
	 */
	@SuppressWarnings("rawtypes")
	public SimulationInputParameter(Enum enumvalue) {
		this.name = enumvalue.getClass().getName();
		this.enumvalue = enumvalue;
		type = SimulationInputType.T_ENUM;
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 */
	public SimulationInputParameter(SimulationInputParameter other) {
		this.name = other.name;
		this.type = other.type;
		switch (type) {
		case T_INT:
			this.ivalue = other.ivalue;
			break;
		case T_DOUBLE:
			this.dvalue = other.dvalue;
			break;
		case T_STRING:
			this.svalue = other.svalue;
			break;
		case T_ENUM:
			this.enumvalue = other.enumvalue;
			break;
		case T_BYTE_ARRAY:
			this.bytevalue = other.bytevalue;
			break;
		default:
			break;
		}
	}

	/**
	 * Creates a simulation input parameter of type byte array
	 * 
	 * @param ivalue
	 */
	public SimulationInputParameter(String name, byte[] bytevalue) {
		this.name = name;
		this.bytevalue = bytevalue;
		type = SimulationInputType.T_BYTE_ARRAY;
	}

	/**
	 * Creates a simulation input parameter of type double
	 * 
	 * @param ivalue
	 */
	public SimulationInputParameter(String name, double dvalue) {
		this.name = name;
		this.dvalue = dvalue;
		type = SimulationInputType.T_DOUBLE;
	}

	/**
	 * Creates a simulation input parameter of type integer
	 * 
	 * @param ivalue
	 */
	public SimulationInputParameter(String name, int ivalue) {
		this.name = name;
		this.ivalue = ivalue;
		type = SimulationInputType.T_INT;
	}

	/**
	 * Creates a simulation input parameter of type string
	 * 
	 * @param ivalue
	 */
	public SimulationInputParameter(String name, String svalue) {
		this.name = name;
		this.svalue = svalue;
		type = SimulationInputType.T_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Implements a content based equality comparison
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SimulationInputParameter)) {
			return false;
		}
		final SimulationInputParameter other = (SimulationInputParameter) o;
		if (!name.equals(other.name)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		switch (type) { 
		case T_DOUBLE: {
			if (dvalue != other.dvalue) {
				return false;
			}
			break;
		}
		case T_INT: {
			if (ivalue != other.ivalue) {
				return false;
			}
			break;
		}
		case T_ENUM: {
			if (!enumvalue.equals(other.enumvalue)) {
				return false;
			}
			break;
		}
		case T_BYTE_ARRAY: {
			if (!enumvalue.equals(other.bytevalue)) {
				return false;
			}
			break;
		}
		case T_STRING: {
			if (!svalue.equals(other.svalue)) {
				return false;
			}
			break;
		}
		default:
			break;
		}
		return true;
	}

	/**
	 * Accessing the value as an bytearray
	 * 
	 * @return
	 * @throws SimulationException
	 */
	public byte[] getByteArray() throws SimulationException {
		if (type.equals(SimulationInputType.T_BYTE_ARRAY)) {
			return bytevalue;
		}
		throw new SimulationException("Attempt to access parameter " + name
				+ " as an integer while it is " + type);
	}

	/**
	 * Accessing the value as a double
	 * 
	 * @return
	 * @throws SimulationException
	 */
	public double getDouble() throws SimulationException {
		if (type.equals(SimulationInputType.T_DOUBLE)) {
			return dvalue;
		}
		throw new SimulationException("Attempt to access parameter " + name
				+ " as a double while it is " + type);
	}

	/**
	 * Accessing the value as an enumeration
	 * 
	 * @return
	 * @throws SimulationException
	 */
	@SuppressWarnings("rawtypes")
	public Enum getEnum() throws SimulationException {
		if (type.equals(SimulationInputType.T_ENUM)) {
			return enumvalue;
		}
		throw new SimulationException("Attempt to access parameter " + name
				+ " as an enumeration while it is " + type);
	}

	/**
	 * Accessing the value as an int
	 * 
	 * @return
	 * @throws SimulationException
	 */
	public int getInt() throws SimulationException {
		if (type.equals(SimulationInputType.T_INT)) {
			return ivalue;
		}
		throw new SimulationException("Attempt to access parameter " + name
				+ " as an integer while it is " + type);
	}

	public String getName() {
		return name;
	}

	/**
	 * Accessing the value as a string
	 * 
	 * @return
	 * @throws SimulationException
	 */
	public String getString() throws SimulationException {
		if (type.equals(SimulationInputType.T_STRING)) {
			return svalue;
		}
		throw new SimulationException("Attempt to access parameter " + name
				+ " as an string while it is " + type);
	}

	/**
	 * Returns the type of the parameter
	 * 
	 * @return
	 */
	public SimulationInputType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * Makes the hashCode compatible with equals
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Formatted printing
	 */
	@Override
	public String toString() {
		switch (type) {
		case T_DOUBLE:
			return name + " = " + dvalue + " (double)";
		case T_INT:
			return name + " = " + ivalue + " (int)";
		case T_STRING:
			return name + " = " + svalue + " (string)";
		case T_ENUM:
			return name + " = " + enumvalue + " (enum)";
		case T_BYTE_ARRAY:
		default:
			throw new Error("this statement should be unreacheable");
		}
		
	}
}
