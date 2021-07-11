const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const User = new Schema({
    patientname: {
        type: String,
        required: true,
        unique: true,
    },
    adhaar: {
        type: String,
        required: true,
    },
    covidstatus: {
        type: String,
        required: true,
    },

});
module.exports = mongoose.model("User",User);