//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-646
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2017.11.17 at 10:51:56 PM EST
//


package checkmarx.wsdl.portal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CxWSResponseResultPaths complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CxWSResponseResultPaths">
 *   &lt;complexContent>
 *     &lt;extension base="{http://Checkmarx.com}CxWSBasicRepsonse">
 *       &lt;sequence>
 *         &lt;element name="Paths" type="{http://Checkmarx.com}ArrayOfCxWSResultPath" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CxWSResponseResultPaths", propOrder = {
    "paths"
})
public class CxWSResponseResultPaths
    extends CxWSBasicRepsonse
{

    @XmlElement(name = "Paths")
    protected ArrayOfCxWSResultPath paths;

    /**
     * Gets the value of the paths property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfCxWSResultPath }
     *
     */
    public ArrayOfCxWSResultPath getPaths() {
        return paths;
    }

    /**
     * Sets the value of the paths property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfCxWSResultPath }
     *
     */
    public void setPaths(ArrayOfCxWSResultPath value) {
        this.paths = value;
    }

}