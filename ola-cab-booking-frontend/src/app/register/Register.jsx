import React, { useEffect } from "react";
import { Grid, TextField, Button } from "@mui/material";
import WestIcon from "@mui/icons-material/West";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { getUser, registerUser } from "@/Redux/Auth/Action";
import { useFormik } from "formik";
import * as yup from "yup";

// Validation schema using Yup
const validationSchema = yup.object().shape({
  fullName: yup.string().required("Full name is required"),
  email: yup.string().email("Invalid email").required("Email is required"),
  password: yup
    .string()
    .min(8, "Password should be at least 8 characters long")
    .required("Password is required"),
  mobile: yup.string().required("Mobile number is required"),
});

const Register = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const { auth } = useSelector((store) => store);

  const goBack = () => {
    router.back();
  };

  const formik = useFormik({
    initialValues: {
      fullName: "",
      email: "",
      password: "",
      mobile: "+91"
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      console.log("values", values);
      dispatch(registerUser(values));
    }
  });

  useEffect(() => {
    if (jwt) {
      dispatch(getUser(jwt));
    }
  }, [jwt]);

  useEffect(() => {
    if (auth.user?.fullName || auth.user?.name) {
      router.push("/book-ride");
    }
  }, [auth.user]);

  return (
    <div>
      {/* Header */}
      <div className="flex items-center px-2 lg:px-5 py-2">
        <WestIcon onClick={goBack} className="cursor-pointer" />
        <div className="w-full flex justify-center">
          <img
            className="h-10"
            src="https://accounts.olacabs.com/en-in/images/ola-logo.png"
            alt="Ola Logo"
          />
        </div>
      </div>

      {/* Registration Form */}
      <form onSubmit={formik.handleSubmit} className="h-full p-5">
        <TextField
          label="User Name"
          variant="outlined"
          fullWidth
          margin="normal"
          name="fullName"
          value={formik.values.fullName}
          onChange={formik.handleChange}
          error={formik.touched.fullName && Boolean(formik.errors.fullName)}
          helperText={formik.touched.fullName && formik.errors.fullName}
        />
        <TextField
          label="Email"
          type="email"
          variant="outlined"
          fullWidth
          margin="normal"
          name="email"
          value={formik.values.email}
          onChange={formik.handleChange}
          error={formik.touched.email && Boolean(formik.errors.email)}
          helperText={formik.touched.email && formik.errors.email}
        />
        <TextField
          label="Password"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          name="password"
          value={formik.values.password}
          onChange={formik.handleChange}
          error={formik.touched.password && Boolean(formik.errors.password)}
          helperText={formik.touched.password && formik.errors.password}
        />
        <TextField
          label="Mobile Number"
          type="tel"
          variant="outlined"
          fullWidth
          margin="normal"
          name="mobile"
          value={formik.values.mobile}
          onChange={formik.handleChange}
          error={formik.touched.mobile && Boolean(formik.errors.mobile)}
          helperText={formik.touched.mobile && formik.errors.mobile}
        />
        <Button
          className="w-full"
          variant="contained"
          type="submit"
          color="secondary"
          sx={{ padding: ".9rem 0rem" }}
        >
          Register
        </Button>
      </form>

      {/* Footer Links */}
      <div className="flex flex-col w-full items-center justify-center space-y-2 mt-5 text-center">
        <p>
          Already have an account?
          <Button onClick={() => router.push("login")} className="ml-2" color="secondary">
            Login
          </Button>
        </p>
        <p>
          Register as a driver
          <Button onClick={() => router.push("/driver/register")} className="ml-2" color="secondary">
            Register
          </Button>
        </p>
      </div>
    </div>
  );
};

export default Register;
