import React from "react"
import NavigationBar from "../../home/components/NavigationBar"
import CirclePlus from '../../../assets/images/plus-circle.png'
import '../styles/viewTrainingPlans.css'
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"

function ViewTrainingPlans () {
    const navigate = useNavigate()
    const [isTrainer, setIsTrainer] = useState(null)
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('userAuth'))
        if (user.userType === "Runner") {
            setIsTrainer(false)
        }
        else {
            setIsTrainer(true)
        }
    })
    const handleClick = (event) => {
        event.preventDefault();
        navigate('/createtrainingplans')
    }
    return (
        <>
            <NavigationBar />
            {isTrainer ? (
                <div className="image-container">
                    <div className="circle-container">
                        <img className="circle-image" src={CirclePlus} onClick={handleClick}/>
                        <div className="tooltip-text-view-training-plans">Crear plan de entrenamiento</div>
                    </div>
                </div> 
            ): (
                <>
                </>
            )}
            
        </>
    )
}

export default ViewTrainingPlans;