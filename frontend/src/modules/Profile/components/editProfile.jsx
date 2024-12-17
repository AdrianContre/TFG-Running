import React from 'react'
import Button from 'react-bootstrap/Button';
import NavigationBar from "../../home/components/NavigationBar";
import Profile from '../../../assets/images/circleUser.png'
import '../styles/editProfile.css'
import { useEffect, useState, useRef } from 'react';
import { updateRunnerProfile, updateTrainerProfile, uploadPicture } from '../services/profileService';
import PopUp from '../../auth/components/PopUp'
import { getUserLogged } from '../../home/services/mainService';
import { Navigate, useNavigate } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faDownload} from '@fortawesome/free-solid-svg-icons'



function EditProfile () {
    const [profileData, setProfileData] = useState({})
    const [name, setName] = useState("")
    const [surname, setSurname] = useState("")
    const [username, setUsername] = useState("")
    const [mail, setMail] = useState("")
    const [height, setHeight] = useState(0)
    const [weight, setWeight] = useState(0)
    const [fcMax, setFcMax] = useState(0)
    const [experience, setExperience] = useState(0)
    const [show, setShow] = useState(false)
    const [picture, setPicture] = useState(null)
    const [modified, setModified] = useState(false)
    const [file, setFile] = useState(null)
    const navigate = useNavigate()
    const fileInputRef = useRef(null);
    const [error, setError] = useState('')
    const [title,setTitle] = useState('')

    const handleClick = async (event) => {
        event.preventDefault()
        let userUpdated;
        if (name === "" || surname === "" || username === "" || mail === "" || height === "" || weight === "" || fcMax === "" || experience === "") {
            setError("Han de estar todos los campos llenos")
            setTitle("Error al editar perfil")
            setShow(true)
        }
        let updateError = false
        let errorMessage = ""
        if (profileData.userType == "Runner") {
            userUpdated = await updateRunnerProfile(profileData.id, name, surname, username, mail, height, weight, fcMax);
            if (userUpdated.error) {
                updateError = true
                errorMessage = userUpdated.error
            }
            else localStorage.setItem('token', userUpdated.token)
        }
        else {
            userUpdated = await updateTrainerProfile(profileData.id,name, surname, username, mail, height, weight, fcMax, experience);
            if (userUpdated.error) {
                updateError = true
                errorMessage = userUpdated.error
            }
            else localStorage.setItem('token', userUpdated.token)
        }
        if (updateError === true) {
            const user = await getUserLogged();
            setTitle("Error al editar perfil")
            if (errorMessage.includes("username")) {
                setError("Ya existe un usuario con ese nombre de usuario")
                setUsername(profileData.username)
            }
            else {
                setError("Ya existe un usuario con ese correo electrónico")
                setMail(profileData.mail)
            }
            setShow(true)
        }
        else {
            if (modified) {
                let formData = new FormData()
                formData.append('profilePicture', file)
                const upload = await uploadPicture(profileData.id,formData)
                console.log(upload)
            }
            const user = await getUserLogged();
            setProfileData(user)
            localStorage.setItem('userAuth', JSON.stringify(user));
            navigate('/profile')
        }
        
    }

    useEffect (() => {
        const loadUser = async () => {
            const user = await  getUserLogged();
            setProfileData(user)
            setName(user.name)
            setSurname(user.surname)
            setUsername(user.username)
            setMail(user.mail)
            setHeight(user.height)
            setWeight(user.weight)
            setFcMax(user.fcMax)
            if (user.userType == "Trainer") {
                setExperience(user.experience)
            }
            if (user.profilePicture !== null) {
                setPicture(`data:image/jpeg;base64,${user.profilePicture}`)
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

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    
    const handleButtonClick = () => {
        fileInputRef.current.click();
    };


    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            onFileSelect(file); 
        }
    };

    const onFileSelect = (file) => {
        const imageUrl = URL.createObjectURL(file);
        setPicture(imageUrl)
        setModified(true)
        setFile(file)
    }
     
      return (
        <>       
            <NavigationBar />
            <div className='container-profile-image'>
                {picture !== null ? (
                   <img src={picture} alt="profile" className='edit-profile-picture'/> 
                ) : (
                    <img src={Profile} alt="profile" className='edit-profile-picture'/>
                )}
               <Button variant="light" style={{marginLeft: '20px'}} onClick={handleButtonClick}><FontAwesomeIcon icon={faDownload} /> Importar</Button>
               <input
                type="file"
                ref={fileInputRef}
                style={{ display: 'none' }}
                onChange={handleFileChange}
                accept="image/*" 
                />
                
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
                        {profileData.userType === "Trainer" ? (
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
            <PopUp error={error} show={show} onHide={onHide} title={title} />
        </>
      )
    }
    


export default EditProfile;