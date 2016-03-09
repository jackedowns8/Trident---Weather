//Plugin file should be always after cordova.js
//There is always better way to create, but this also works

window.shortToast = function(str, callback) {   
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "ToastPlugin", "shortToast", [ str ]);
};

window.longToast = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "ToastPlugin", "longToast", [ str ]);
};