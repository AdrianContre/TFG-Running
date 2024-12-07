import React, { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import Profile from '../../../assets/images/circleUser.png'
import '../styles/viewProfile.css'
import Button from 'react-bootstrap/Button';
import Zones from "./zones";
import { useEffect } from "react";
import { deleteProfile, getRunnerZones, getTrainerZones } from "../services/profileService";
import { Navigate, useNavigate } from "react-router";
import Modal from 'react-bootstrap/Modal';

function ViewProfile () {
    const userAuth = JSON.parse(localStorage.getItem("userAuth"))

    const [heartZones, setHeartZones] = useState({})
    const [picture, setPicture] = useState(null)
    const [show,setShow] = useState(false)

    const navigate = useNavigate()

    useEffect (() => {
        const getHeartRateZones = async () => {
            if (userAuth.userType == "Runner") {
                const zones = await getRunnerZones(userAuth.id);
                setHeartZones(zones.data)
            }
            else {
                const zones = await getTrainerZones(userAuth.id);
                setHeartZones(zones.data)
            }

            if (userAuth.profilePicture !== null) {
                setPicture(`data:image/jpeg;base64,${userAuth.profilePicture}`)
            }
        }
        getHeartRateZones()
        
    },[])

    const handleReference = (event) => {
        event.preventDefault()
        navigate('/editprofile')
    }

    const navigateToMaterials = (event) => {
        event.preventDefault()
        navigate('/listmaterials')
    }

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    const handleDeleteProfile = (event) => {
        event.preventDefault()
        setShow(true)
    }

    const handleDeleteProfileAPI = async (event) => {
        event.preventDefault()
        try {
            const deleteProf = await deleteProfile(userAuth.id)
            localStorage.removeItem('token')
            localStorage.removeItem('userAuth')
            navigate('/')
        }
        catch (error) {
            alert(error)
            console.log(error)
        }
        
    }

    return (
        <>
            <NavigationBar/>
            <div className="profile-layout">
                <div className="profile-left">
                    <div className='profilecontainer'>
                        {picture !== null ? (
                        <img src={picture} alt="profile" className="profile-picture"/>)
                    : (<img src={Profile} alt="profile" className="profile-picture"/>)}
                        
                        <div className="profile-details">
                            <h2 className="profile-name">{userAuth.name} {userAuth.surname}</h2>
                            <p className="profile-username">@{userAuth.username}</p>
                        </div>
                    </div>
                    <div className="button-container-profile">
                        <Button variant="primary" size="lg" className="custom-button-view-profile" onClick={navigateToMaterials}>MATERIAL</Button>
                    </div>
                    {userAuth.fcMax !== 0 ? (
                        <div>
                            <p style={{fontWeight: 'bold', fontSize: '16px', marginTop: '30px', marginLeft: '50px'}}>Zonas de frecuencia cardiaca basadas en tu fcMax:</p>
                            <Zones zones={heartZones}/>
                        </div>
                    ) : (
                        <div>
                            <a className="custom-reference" onClick={handleReference}>Edita tu fcMax para poder ver tus zonas de entrenamiento!!</a>
                        </div>
                    )}
                </div>

                <div className="profile-right">
                    {/* En un futuro aqui habrán las estadísticas del usuario */}    
                </div>
            </div>
            <div className="delete-button-container">
                <Button variant="danger" size="lg" className="button-delete" onClick={handleDeleteProfile}>ELIMINAR PERFIL</Button>
            </div>

            <Modal show={show} onHide={onHide} backdrop="static" keyboard={false}>
                <Modal.Header>
                <Modal.Title>Eliminar perfil</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                Estás seguro que quieres eliminar el perfil ? 
                </Modal.Body>
                <Modal.Footer>
                <div className="pop-up-buttons">
                    <Button variant="danger" size="lg" onClick={handleDeleteProfileAPI}>
                        OK
                    </Button>
                    <Button variant="secondary" size="lg" onClick={onHide}>
                        CANCELAR
                    </Button>
                </div>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default ViewProfile;