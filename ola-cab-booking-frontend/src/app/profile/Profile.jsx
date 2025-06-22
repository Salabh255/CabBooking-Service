'use client'
import { Avatar, Button } from '@mui/material'
import { deepOrange } from '@mui/material/colors'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'
import WestIcon from "@mui/icons-material/West"
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet'
import ContactPhoneIcon from '@mui/icons-material/ContactPhone'
import WifiIcon from '@mui/icons-material/Wifi'
import RideCard from '../components/user/RideCard/RideCard'
import { getUser } from '@/Redux/Auth/Action'
import { useDispatch, useSelector } from 'react-redux'

const Profile = () => {
    const dispatch= useDispatch()
    const jwt = localStorage.getItem("jwt")
    const {auth} = useSelector(state=>state)
    const router = useRouter()
    const goBack=()=>{
        router.back()
    }

    useEffect(()=>{
        dispatch(getUser(jwt))
    },[])

    comsole.log("auth",auth)

    const handleLogout=()=>{
        dispatch(logout())
        router.push("/book-ride")
    }

  return (
    <div className='px-2 lg:px-5'>
        <div className='px-2 lg:px-5 py-2'>
            <WestIcon onClick={goBack} className='cursor-pointer' />
        </div>

        <div className='flex flex-col items-center space-y-2'>
            <Avatar sx={{bgcolor:deepOrange[500]}}>{auth.user?.fullName[0]}</Avatar>
            <p>{auth.user?.fullName}</p>
            <p>{auth.user?.mobile}</p>
        </div>

        <div className='border rounded-sm mt-5'>
            <RideCard/>

            <div className='flex flex-col items-center p-3' >
                <Button onClick={()=>router.push("/your-rides")} variant='text'>See all rides</Button>
            </div>
        </div>

        <div className='border mt-5'>
            <div className='flex items-center p-3 border-b'>
                 <AccountBalanceWalletIcon className='text-green-600'/>
                 <p className='ml-4'>ola money</p>
            </div>
            <div className="flex items-center p-3 border-b">
                <ContactPhoneIcon/>
                <p className="ml-4">Emergency Contact</p>
            </div>
            <div className="flex items-center p-3">
                <WifiIcon/>
                <p className="ml-4">Ola Wi-fi Credentials</p>
            </div>
        </div>

        <div>
            <Button onClick={handleLogout} className='w-full bg-red-500 text-white' variant='contained' color='error'>Logout</Button>
        </div>

    </div>
  )
}

export default Profile