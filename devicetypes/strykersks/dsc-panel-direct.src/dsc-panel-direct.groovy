/*
 *  DSC Alarm Panel Direct
 *
 *  Author: Sean Kendall Schneyer <sean@linuxbox.org>
 *  Date: 2015-11-14
 *  
 *  Inspired by and combines ideas from obycode, Kent Holloway <drizit@gmail.com> and oehokie
 *  Also inspired by Radio Thermostat for direct communication examples:
 *    https://github.com/statusbits/smartthings/blob/master/devicetypes/statusbits/radio-thermostat.src/radio-thermostat.groovy
 *
 */
 
 preferences {
    input("confIpAddr", "string", title:"Envisalink (Proxy) IP Addr",
        required:true, displayDuringSetup: true)
    input("confTcpPort", "number", title:"Envisalink Port",
        required:true, displayDuringSetup:true)
}

metadata {
    definition (name:"DSC Alarm Panel Direct", namespace:"StrykerSKS", author:"Sean Kendall Schneyer <sean@linuxbox.org>") {
    
        // Define Capabilities Here
        capability "Alarm"
        capability "Refresh"
        capability "Polling"
        capability "Switch"
        

        // Add commands here as needed
        }
