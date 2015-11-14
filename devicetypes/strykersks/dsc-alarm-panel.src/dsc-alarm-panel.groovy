/*
 *  DSC Alarm Panel
 *
 *  Author: Sean Kendall Schneyer <sean@linuxbox.org>
 *  Date: 2015-11-14
 *  
 *  Inspired by and combines ideas from obycode, Kent Holloway <drizit@gmail.com> and oehokie
 *
 */

// for the UI
metadata {
  // Automatically generated. Make future change here.
  definition (name: "DSC Alarm Panel", namespace: "StrykerSKS", author: "Sean Kendall Schneyer <sean@linuxbox.org>") {
    // Change or define capabilities here as needed
    capability "Refresh"
    capability "Polling"
    capability "Alarm"
    capability "Switch"

    // Add commands as needed
    command "partition"
    
    command "armAway"
    command "armStay"
    command "disarm"
    command "clear"
    command "update"
    command "chimeToggle"
    command "panic"
  }

  simulator {
    // Nothing here, you could put some testing stuff here if you like
  }

  tiles {
    standardTile("alarmStatus", "device.alarmStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
      state "ready", label: 'Ready', action: "armAway", icon: "st.Home.home2", backgroundColor: "#ffffff"
      state "disarmed", label: 'Ready', action: "armAway", icon: "st.Home.home2", backgroundColor: "#ffffff"
      state "notready", label: 'Not Ready', icon: "st.Home.home2", backgroundColor: "#ffa81e"
      state "away", label: 'Away', action: "disarm", icon: "st.Home.home3", backgroundColor: "#add8e6"
      state "stay", label: 'Stay', action: "disarm", icon: "st.Home.home4", backgroundColor: "#0000ff"
      state "arming", label: 'Arming', action: "disarm", icon: "st.Home.home2", backgroundColor: "#B8B8B8"
      state "alarm", label: 'Alarm', action: "clear", icon: "st.Home.home2", backgroundColor: "#ff0000"
    }
    standardTile("away", "device.switchAway", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
      state "on", label: "Away", action: "armAway", icon: "st.Home.home3", backgroundColor: "#add8e6"
      state "off", label: "Away", icon: "st.Home.home3", backgroundColor: "#ffffff"
    }
    standardTile("stay", "device.switchStay", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
      state "on", label: "Stay", action: "armStay", icon: "st.Home.home4", backgroundColor: "#0000ff"
      state "off", label: "Stay", icon: "st.Home.home4", backgroundColor: "#ffffff"
    }
    standardTile("chime", "device.chime", width:1, height: 1, canChangeIcon: false, canChangeBackground: false) {
      state "chimeOff", label:'Chime', action:'chimeToggle', icon:"st.secondary.off", backgroundColor: "#ffffff"
      state "chimeOn", label:'', action:'chimeToggle', icon:"st.secondary.beep", backgroundColor: "#ffffff"
    }
    standardTile("panic", "device.panic", width: 1, height: 1, canChangeIcon: false, canChangeBackground: true) {
      state "panic", label:'Panic', action:"panic", icon:"st.alarm.alarm.alarm", backgroundColor:"#ff0000"
      //state "confirm", label:'Confirm', action: "panic", icond:"st.alarm.alarm.alarm", backgroundColor:"#ff6600"
      //state "cancel", label:'Cancel', action: "disarm", icond:"st.alarm.alarm.alarm", backgroundColor:"#ff6600"
    }
    standardTile("refresh", "device.refresh", inactiveLabel: false, width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
      state "default", action:"update", icon:"st.secondary.refresh"
    }
    main(["alarmStatus"])
    details(["alarmStatus","away","stay","chime","refresh","panic"])
  }
  
}

// parse events into attributes
def parse(String description) {
  // log.debug "Parsing '${description}'"
  def myValues = description.tokenize()

  log.debug "Event Parse function: ${description}"
  sendEvent (name: "${myValues[0]}", value: "${myValues[1]}")
}

def partition(String state, String partition) {
    // state will be a valid state for the panel (ready, notready, armed, etc)
    // partition will be a partition number, for most users this will always be 1

    log.debug "Partition: ${state} for partition: ${partition}"
    sendEvent (name: "dscpartition", value: "${state}")
}

def poll() {
  log.debug "Executing 'poll'"
  // TODO: handle 'poll' command
  // On poll what should we do? nothing for now..
}

def refresh() {
  log.debug "Executing 'refresh' which is actually poll()"
  poll()
  // TODO: handle 'refresh' command
}
