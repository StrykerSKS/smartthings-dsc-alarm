/*
 *  DSC Alarm Panel
 *
 *  Author: Sean Kendall Schneyer <sean@linuxbox.org>
 *  Date: 2015-11-14
 *  
 *  Inspired by and combines ideas from obycode and Kent Holloway <drizit@gmail.com>
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
    standardTile("dscpartition", "device.dscpartition", width: 2, height: 2, canChangeBackground: true, canChangeIcon: true) {
      state "armed",     label: 'Armed',      backgroundColor: "#79b821", icon:"st.Home.home3"
      state "exitdelay", label: 'Exit Delay', backgroundColor: "#ff9900", icon:"st.Home.home3"
      state "entrydelay",label: 'EntryDelay', backgroundColor: "#ff9900", icon:"st.Home.home3"
      state "notready",  label: 'Open',       backgroundColor: "#ffcc00", icon:"st.Home.home2"
      state "ready",     label: 'Ready',      backgroundColor: "#79b821", icon:"st.Home.home2"
      state "alarm",     label: 'Alarm',      backgroundColor: "#ff0000", icon:"st.Home.home3"
    }

    main "dscpartition"

    // These tiles will be displayed when clicked on the device, in the order listed here.
    details(["dscpartition"])
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
