'use client'
import React, { useState } from 'react'
import {
    AppBar,
    Toolbar,
    IconButton,
    Typography,
    Button,
    Drawer,
    Avatar,
    Box
  } from '@mui/material'
import MenuIcon from '@mui/icons-material/Menu'
import { deepOrange } from '@mui/material/colors'
import { drawerList } from './DrawerList'
import { useDispatch, useSelector } from 'react-redux'
import { useRouter } from 'next/router'

const BookRideNavbar = () => {
    
    const[sidebarOpen, setSideBarOpen]=useState(false)
    const dispatch = useDispatch()
    const jwt=localStorage.getItem("jwt")
    const {auth} = useSelector(state=>state)
    
    const handleSidebarClose=()=>setSideBarOpen(false)
    const handleSidebarOpen=()=>setSideBarOpen(true)
    
    const router = useRouter()

    useEffect(()=>{
            dispatch(getUser(jwt))
        },[])

    return (
        <Box>
            <AppBar sx={{ backgroundColor: "#120E43" }} position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={handleSidebarOpen}
                    >
                        <MenuIcon />
                    </IconButton>

                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Zosh Cab
                    </Typography>

                    {auth.user?.fullName ? (
                        <Avatar
                            className="cursor-pointer"
                            sx={{ bgcolor: deepOrange[500] }}
                            onClick={()=>router.push("/profile")}
                        >
                            {auth.user?.fullName[0]}
                        </Avatar>
                    ) : (
                        <Button onClick={()=>router.push("/login")} color="inherit">Login</Button>
                    )}
                </Toolbar>
            </AppBar>

            <Drawer anchor="left" open={sidebarOpen} onClose={handleSidebarClose}>
                {drawerList("left")}
            </Drawer>
        </Box>
    )
}

export default BookRideNavbar