const  express = require("express");
const mongoose = require("mongoose");
const { post } = require("./routes/user");
const app = express();
const router = express.Router();


const port = process.env.PORT || 5000;
mongoose.connect("mongodb+srv://admin:prem@$ifsb@cluster0.lircg.mongodb.net/PatientDB?retryWrites=true&w=majority", {
useNewUrlParser: true,
useCreateIndex: true,
useUnifiedTopology: true,});


const connection = mongoose.connection;
connection.once("open",()=>{
    console.log("Mongodb connected");
});
//middleware
app.use(express.json());

const userRoute = require("./routes/user");
app.use("/user",userRoute);


app.route("/").get((req, res) => res.json("Hello World "));
app.listen(port,() => console.log(`your server is runing on port ${port}`)); 