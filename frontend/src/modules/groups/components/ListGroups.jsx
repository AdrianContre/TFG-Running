import { useEffect } from "react";
import { useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import CirclePlus from '../../../assets/images/plus-circle.png'
import { useNavigate } from "react-router";

function ListGroups() {
    const [isTrainer, setIsTrainer] = useState(false)
    const navigate = useNavigate()
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('userAuth'))
        if (user.userType === "Trainer") {
            setIsTrainer(true)
        }
    },[])

    const handleClick = (event) => {
        event.preventDefault()
        navigate('/creategroup')
    }
    return (
        <>
            <NavigationBar />
            {isTrainer === true ? (<div className="image-container">
                <div className="circle-container">
                    <img className="circle-image" src={CirclePlus} onClick={handleClick}/>
                    <div className="tooltip-text">Crear grupo</div>
                </div>
            </div>) : (null)}
        </>
    )
}


export default ListGroups;