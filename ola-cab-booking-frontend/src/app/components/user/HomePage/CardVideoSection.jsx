import React from 'react'


const CardVideoSection = () => {
  return (
    <div className='mt-32'>
      <h1 className='text-5xl font-bold my-10 text-center'>India's most ambitious car</h1>
      <div>
        <video autoPlay muted loop controls style={{width:'86%', height:'90%', display:'block', margin:'0 auto'}} src="/4228659-hd_1280_720_50fps.mp4"></video>
      </div>
    </div>
  )
}
export default CardVideoSection