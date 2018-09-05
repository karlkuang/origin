#!/bin/bash


device_serial=""
normal_gms=""

GREEN='\e[0;32m'
YELLOW='\e[1;33m'
RED='\e[1;31m'
END='\e[0m'

RED()
{
    echo -e  "${RED}$1${END}"
}

GREEN()
{
    echo -e  "${GREEN}$1${END}"
}

YELLOW()
{
    echo -e  "${YELLOW}$1${END}"
}

result_list_for_go() {
    YELLOW "Browser -------------------------11"
    YELLOW "Messaging -----------------------11"
    YELLOW "Email ------------------------11111"
    YELLOW "Keyboard ------------------------10"
    YELLOW "VoiceAssistant -------------------1"
    YELLOW "Search ---------------------------1"
    echo
}

result_list() {
    YELLOW "Browser -------------------------11"
    YELLOW "Gallery -----------------------1111"
    YELLOW "Messaging -----------------------11"
    YELLOW "Calendar -------------------------1"
    YELLOW "Email ------------------------11111"
    YELLOW "Keyboard ------------------------10"
    YELLOW "VoiceAssistant -------------------1"
    YELLOW "Search ---------------------------1"
    echo
}

pm_clear_app() {
    go_app_list=("com.android.chrome"
              "com.google.android.apps.messaging"
              "com.google.android.gm.lite"
              "com.google.android.apps.assistant"
              "com.google.android.apps.searchlite"
              )
    normal_app_list=("com.android.chrome"
              "com.google.android.apps.photos"
              "com.google.android.apps.messaging"
              "com.google.android.calendar"
              "com.google.android.gm"
              "com.google.android.googlequicksearchbox"
              )

    if [[ -n $normal_gms ]]; then
       for ((i = 0; i < ${#normal_app_list[@]}; i++)); do
           adb shell pm clear "${normal_app_list[$i]}" 1> /dev/null   
       done
    else
       for ((i = 0; i < ${#go_app_list[@]}; i++)); do
           adb shell pm clear "${go_app_list[$i]}" 1> /dev/null   
       done 
    fi 
    
    if [[ $? -ge 0 ]]; then
       GREEN "app list clear Success!"
    fi
}

check_app() {

    if [[ -n $normal_gms ]]; then
        YELLOW "Normal gms express plus apps check"
    else
        YELLOW "Go edition express plus apps check"
    fi

    echo
    RED "Browser:"
    adb shell "am start -W -a android.intent.action.VIEW -d 'https://www.wikipedia.org/' | grep -c com.android.chrome"
    adb shell "am force-stop com.android.chrome"
    adb shell "am start -W -a android.intent.action.VIEW -d 'http://www.wikipedia.org/' | grep -c com.android.chrome"

    if [[ -n $normal_gms ]]; then
        RED "Gallery:"
        adb shell "am start -W -t image/* file://tmp/cute.jpg | grep -c com.google.android.apps.photos"
        adb shell "am start -W -t image/* file://tmp/cute.mp4 | grep -c com.google.android.apps.photos"
        adb shell "am start -W -a android.intent.action.PICK -t image/* | grep -c com.google.android.apps.photos"
        adb shell "am start -W -a com.android.camera.action.REVIEW -t image/* | grep -c com.google.android.apps.photos"
    fi

    RED "Messaging:"
    adb shell "am start -W -a android.intent.action.SENDTO -d sms:CCXXXXXXXXXX | grep -c com.google.android.apps.messaging"
    adb shell "am start -W -a android.intent.action.SENDTO -d smsto:CCXXXXXXXXXX | grep -c com.google.android.apps.messaging"

    if [[ -n $normal_gms ]]; then
        RED "Calendar:"
        adb shell "am start -W -a android.intent.action.VIEW -d content://com.android.calendar/time/1410665898789 | grep -c com.google.android.calendar"
    fi

    if [[ -n $normal_gms ]]; then
        RED "Email:"
        adb shell "am start -W -a android.intent.action.SENDTO -d mailto:someone@gmail.com | grep -c com.google.android.gm"
        sleep 1
        adb shell dumpsys package com.google.android.gm | grep -c "android.permission.READ_CALENDAR: granted=true" 
        adb shell dumpsys package com.google.android.gm | grep -c "android.permission.WRITE_CALENDAR: granted=true"
        adb shell dumpsys package com.google.android.gm | grep -c "android.permission.READ_CONTACTS: granted=true"
        adb shell dumpsys package com.google.android.gm | grep -c "android.permission.WRITE_CONTACTS: granted=true"
    else
        RED "Email:"
        adb shell "am start -W -a android.intent.action.SENDTO -d mailto:someone@gmail.com | grep -c com.google.android.gm.lite"
        sleep 2
        adb shell dumpsys package com.google.android.gm.lite | grep -c "android.permission.READ_CALENDAR: granted=true"
        adb shell dumpsys package com.google.android.gm.lite | grep -c "android.permission.WRITE_CALENDAR: granted=true"
        adb shell dumpsys package com.google.android.gm.lite | grep -c "android.permission.READ_CONTACTS: granted=true"
        adb shell dumpsys package com.google.android.gm.lite | grep -c "android.permission.WRITE_CONTACTS: granted=true"        
    fi

    RED "Keyboard:"
    adb shell "settings get secure default_input_method | grep -c com.google.android.inputmethod"
    adb shell "ime list -a | grep mId | grep -v -c mId=com.google.android"

    if [[ -n $normal_gms ]]; then
        RED "Voice Assistant:"
        adb shell "am start -W -a android.intent.action.VOICE_COMMAND | grep -c com.google.android.googlequicksearchbox"
    else
        RED "Voice Assistant:"
        adb shell "am start -W -a android.intent.action.ASSIST | grep -c com.google.android.apps.assistant"        
    fi

    if [[ -n $normal_gms ]]; then
        RED "Search:"
        adb shell "am start -W -a android.intent.action.WEB_SEARCH -e query wikipedia | grep -c com.google.android.googlequicksearchbox"
    else
        RED "Search:"
        adb shell "am start -W -a android.intent.action.WEB_SEARCH -e query wikipedia | grep -c com.google.android.apps.searchlite"        
    fi
}

getprop() {
    RED "GMSEXPRESS_PLUS_BUILD"
    adb shell "pm list features"
    echo
    RED "low_ram:"
    adb shell "getprop | grep ro.config.low_ram"
    echo
    RED "security:"
    adb shell "getprop | grep security"
    echo
    RED "fingerprint:"
    adb shell "getprop | grep fingerprint"
    echo
    RED "system model device name:"
    adb shell "getprop | grep ro.product.name"
    adb shell "getprop | grep ro.product.device"
    adb shell "getprop | grep ro.product.model"
    adb shell "getprop | grep ro.product.brand"
    adb shell "getprop | grep ro.product.manufacturer"
    echo
    RED "vendor model device name:"
    adb shell "getprop | grep ro.vendor.product.name"
    adb shell "getprop | grep ro.vendor.product.device"
    adb shell "getprop | grep ro.vendor.product.model"
    adb shell "getprop | grep ro.vendor.product.brand"
    adb shell "getprop | grep ro.vendor.product.manufacturer"
    echo
    RED "build.version.incremental & release:"
    adb shell "getprop | grep ro.build.version.incremental"
    adb shell "getprop | grep ro.build.version.release"
    echo
    RED "build_type:"
    adb shell "getprop | grep ro.build.id"
    adb shell "getprop | grep ro.build.type"
    adb shell "getprop | grep ro.build.tags"
    echo
    RED "google.gmsversion:"
    adb shell "getprop | grep ro.com.google.gmsversion"
    echo
    RED "density:"
    adb shell "getprop | grep density"
    echo
    RED "clientid:"
    adb shell "getprop | grep clientid"
    echo
    RED "first_api_level:"
    adb shell "getprop | grep first_api_level"
    echo
}

choose_gms_version() {
    YELLOW "========= 1. Normal GMS ==========="
    YELLOW "========= 2. Go edition ==========="
    echo
    echo "Which would you like:"
    read cmd
    if [ $cmd = "1" ];then
      result_list
      normal_gms="yes"
      check_app
    fi
    if [ $cmd = "2" ];then
      result_list_for_go
      normal_gms=""
      check_app
    fi
}

get_device_serial() {
    device_serial_array=($(adb devices|awk "{if(found) print} /List of devices attached/{found=1}"|awk '{gsub("device", "");print}'|tr -d "\r\n"))
    device_count="${#device_serial_array[@]}"

    if [[ "$device_count" -lt 1 ]]; then
      echo "No device found" && exit 1
    elif [[ "$device_count" -eq 1 ]]; then
      device_serial="${device_serial_array[0]}"
      return
    fi

    if [[ -n "$device_serial" ]]; then
      export ANDROID_SERIAL=$device_serial
      return
    fi

    device_index=-1
    i=1
    indent="  "
    echo "Found the following devices:"
    for device_serial in "${!device_serial_array[@]}"; do
      device_serial="${device_serial_array[$device_serial]}"
      device_name=$(adb -s "$device_serial" shell getprop "ro.product.name"|tr -d '\r')
      echo "$indent[$i] $device_name ($device_serial)"
      i=$((i+1))
    done
    echo ""
    echo -n "Select device to test: [1-$device_count] "
    read -r device_index

    if [[ $device_index -lt 1 || $device_index -gt $device_count ]]; then
      echo "Invalid device selection" && exit 1
    else
      device_serial="${device_serial_array[$device_index - 1]}"
      if [[ -n $device_serial ]]; then
        export ANDROID_SERIAL=$device_serial
      fi
    fi
    echo ""
}

main() {
    get_device_serial
    echo     
    choose_gms_version   
    echo
    YELLOW "********* start getprop ***********"
    getprop
    pm_clear_app
    YELLOW "============= end ================="
}

main "$@"
