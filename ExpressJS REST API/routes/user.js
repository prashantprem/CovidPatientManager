const express = require("express");
const User = require("../models/user_model");
const router = express.Router();



router.get("/all",(req,res)=>{
    User.find((err,result)=>{
        if(err) res.status(500).json({msg:err});
            res.json({
                data: result
            });

    });

    

});

router.get("/:adhaar",(req,res)=>{
    User.findOne(
        {adhaar: req.params.adhaar},
        (err,result)=>{
            if(err) res.status(500).json({msg:err});
            res.json({
                data: result,
                adhaar: req.params.adhaar,
            });
        }
    );

});

// router.post("/login",(req,res)=>{
//   User.findOne(
//       {username: req.body.username},
//       (err,result)=>{
//           if(err) return res.status(500).json({msg:err});
//           if(result == null){
//               return res.status(403).json("Username is incorect");
//           }
//           if(result.password == req.body.password){
//               //jwt
//              let token = jwt.sign({username:req.body.username},config.key,
//                   {
//                       expiresIn: "24h",

//                   });
//               res.json({
//                   token: token,
//                   msg: "Success",
//               });
//           }
//           else{
//                res.status(403).json("Password is incorrect");
//           }
//       }
//   );
    
// });

router.post('/register',(req,res)=>
{
    console.log("Inside the register");
    const user = new User({
        patientname: req.body.patientname,
        adhaar: req.body.adhaar,
        covidstatus: req.body.covidstatus,
    });
    user
    .save()
    .then(()=>{
        console.log("Patient Added");
        res.status(200).json("ok");
    })
    .catch((err)=>{
        res.status(403).json({msg: err});
    });     

});
router.put("/update/:adhaar",(req,res)=>{
   User.findOneAndUpdate(
       {adhaar: req.params.adhaar},
       {$set: {covidstatus: req.body.covidstatus}},
       (err,result)=>{
           if(err) return res.status(500).json({msg: err});
           const msg = {
               msg: "Status successfully updated",
               adhaar: req.params.adhaar,
           };
           return res.json(msg);
       }

   );
});
router.delete("/delete/:adhaar",(req,res)=>{
   User.findOneAndDelete(
       {adhaar: req.params.adhaar},
       (err,result)=>{
           if(err) return res.status(500).json({msg: err});
           const msg = {
               msg: "Patient deleted",
               adhaar: req.params.adhaar,
           };
           return res.json(msg);
       }
   );
});
module.exports = router;