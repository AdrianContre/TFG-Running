import { useState } from "react";
import { useEffect } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import { getUserMaterials } from "../services/materialService";
import MaterialCard from "./MaterialCard";
import '../styles/listMaterials.css'
import { Button } from "react-bootstrap";

function ListMaterials () {

    const [materials,setMaterials] = useState([])

    useEffect(() => {
        const id = JSON.parse(localStorage.getItem("userAuth")).id
        const getMaterialsUser = async () => {
            const userMaterials = await getUserMaterials(id);
            setMaterials(userMaterials)
        }
        getMaterialsUser()
    },[])


    return (
        <>
        <NavigationBar/>
        <div className="title-container">
            <h1>Materiales</h1>
        </div>
        <div className="material-list">
            {materials.map((material, index) => (
                <MaterialCard key={index} material={material} />
            ))}
        </div>
        <div className="title-container">
            <Button className="custom-material-button" variant="primary" size="lg">Añadir material</Button>
        </div>
        </>
    )
}

export default ListMaterials;