import React, { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import Profile from '../../../assets/images/circleUser.png'
import '../styles/viewProfile.css'
import Button from 'react-bootstrap/Button';
import Zones from "./zones";
import { useEffect } from "react";
import { getRunnerZones, getTrainerZones } from "../services/profileService";
import { Navigate, useNavigate } from "react-router";

function ViewProfile () {
    const userAuth = JSON.parse(localStorage.getItem("userAuth"))

    const [heartZones, setHeartZones] = useState({})

    const navigate = useNavigate()

    useEffect (() => {
        console.log("entro aqui")
        const getHeartRateZones = async () => {
            if (userAuth.userType == "Runner") {
                const zones = await getRunnerZones(userAuth.id);
                setHeartZones(zones.data)
            }
            else {
                const zones = await getTrainerZones(userAuth.id);
                console.log(zones.data)
                setHeartZones(zones.data)
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

    return (
        <>
            <NavigationBar/>
            <div className="profile-layout">
                <div className="profile-left">
                    <div className='profilecontainer'>
                        <img src={Profile} alt="profile" style={{ width: '150px', height: '150px' }}/>
                        <div className="profile-details">
                            <h2 className="profile-name">{userAuth.name} {userAuth.surname}</h2>
                            <p className="profile-username">@{userAuth.username}</p>
                        </div>
                    </div>
                    <div className="button-container-profile">
                        <Button variant="primary" size="lg" className="custom-button-view-profile" onClick={navigateToMaterials}>Material</Button>
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
        </>
    )
}

export default ViewProfile;