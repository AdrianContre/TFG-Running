import React from 'react'
import { User, Lock, Edit } from "lucide-react"
import Button from 'react-bootstrap/Button';
import NavigationBar from "../../home/components/NavigationBar";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleUser} from '@fortawesome/free-solid-svg-icons'
import Profile from '../../../assets/images/circleUser.png'
import '../styles/editProfile.css'
import { useEffect, useState } from 'react';
import { updateRunnerProfile, updateTrainerProfile } from '../services/profileService';



function EditProfile () {
    const profileData = JSON.parse(localStorage.getItem("userAuth"));
      const [name, setName] = useState("")
      const [surname, setSurname] = useState("")
      const [username, setUsername] = useState("")
      const [mail, setMail] = useState("")
      const [height, setHeight] = useState(0)
      const [weight, setWeight] = useState(0)
      const [fcMax, setFcMax] = useState(0)
      const [experience, setExperience] = useState(0)

    const handleClick = async (event) => {
        event.preventDefault()
        if (profileData.userType == "Runner") {
            const query = await updateRunnerProfile(profileData.id, name, surname, mail, height, weight, fcMax);
        }
        else {
            const query = await updateTrainerProfile(profileData.id, name, surname, mail, height, weight, fcMax,experience);
        }
        

    }

    useEffect (() => {
        const loadUser = () => {
            setName(profileData.name)
            setSurname(profileData.surname)
            setUsername(profileData.username)
            setMail(profileData.mail)
            setHeight(profileData.height)
            setWeight(profileData.weight)
            setFcMax(profileData.fcMax)
            if (profileData.userType == "Trainer") {
                setExperience(profileData.experience)
            }
        }
        loadUser()  
    },[])

    const handleNameChange = (event) => {
        setName(event.target.value)
    }

    const handleSurnameChange = (event) => {
        setSurname(event.target.value)
    }

    const handleUsernameChange = (event) => {
        setUsername(event.target.value)
    }

    const handleMailChange = (event) => {
        setMail(event.target.value)
    }

    const handleWeightChange = (event) => {
        setWeight(event.target.value)
    }

    const handleHeightChange = (event) => {
        setHeight(event.target.value)
    }

    const handleFcMaxChange = (event) => {
        setFcMax(event.target.value)
    }

    const handleExperienceChange = (event) => {
        setExperience(event.target.value)
    }
     
      return (
        <>       
            <NavigationBar />
            <div className='container-profile-image'>
                <img src={Profile} alt="profile" style={{ width: '150px', height: '150px' }}/>
            </div>
            <div className="container-column">
                <div className="row">
                    <div className="col">
                        <div className='row'>
                            <label className="custom-label-profile">NOMBRE</label>
                            <input name="name" value={name} onChange={handleNameChange} className="custom-input-profile"/>
                        </div>
                        <div className='row'>
                            <label className="custom-label-profile">APELLIDOS</label>
                            <input name="surname" value={surname} onChange={handleSurnameChange} className="custom-input-profile"/>
                        </div>
                        <div className='row'>
                            <label className="custom-label-profile">NOMBRE DE USUARIO</label>
                            <input name="username" value={username} onChange={handleUsernameChange} className="custom-input-profile"/>
                        </div>
                        <div className='row'>
                            <label className="custom-label-profile">CORREO ELECTRÓNICO</label>
                            <input name="mail" value={mail} onChange={handleMailChange} className="custom-input-profile"/>
                        </div>
                    </div>

                    
                    <div className="col" style={{marginLeft: '30%'}}>
                        <div className='row'>
                            <label className="custom-label-profile">ALTURA (cm)</label>
                            <input name="height" type="number" value={height} onChange={handleHeightChange} className="custom-input-profile" />
                        </div>
                        <div className='row'>
                            <label className="custom-label-profile">PESO (kg)</label>
                            <input name="weight" type="number" value={weight} onChange={handleWeightChange} className="custom-input-profile"/>
                        </div>
                        <div className='row'>
                            <label className="custom-label-profile">FC MAX (ppm)</label>
                            <input name="fcMax" type="number" value={fcMax} onChange={handleFcMaxChange} className="custom-input-profile" />
                        </div>
                        {profileData.userType ? (
                            <div className='row'>
                                <label className="custom-label-profile">EXPERIENCIA (años)</label>
                                <input name="experience" type="number" value={experience} onChange={handleExperienceChange} className="custom-input-profile" />
                            </div>
                        ): <></>}
                    </div>
                </div>
            </div>
            <div className='button-container-edit-profile'>
                <Button className='save-button' onClick={handleClick}>GUARDAR CAMBIOS</Button>
            </div>
        </>
      )
    }
    


export default EditProfile;