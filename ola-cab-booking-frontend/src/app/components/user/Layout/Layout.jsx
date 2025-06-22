import React from 'react'

const Layout = ({ Children }) => {
  return (
    <div className="h-screen flex">
      {/* Left Section */}
      <div className="flex-1">
        {Children}
      </div>

      {/* Right Image Section */}
      <div className="hidden md:block md:w-3/5 h-full">
        <img 
          className="w-full h-full object-cover object-right-top" 
          src="/pexels-cottonbro-4606338.jpg" 
          alt="Cab Booking"
        />
      </div>
    </div>
  )
}

export default Layout
