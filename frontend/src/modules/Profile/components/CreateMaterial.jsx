import React from "react";
import NavigationBar from "../../home/components/NavigationBar";
import Button from 'react-bootstrap/Button';
import { useState } from "react";
import { createMaterial } from "../services/materialService";
import { useNavigate } from "react-router";
import '../styles/createMaterial.css'
import PopUp from "../../auth/components/PopUp";

function CreateMaterial () {
    const [brand, setBrand] = useState("")
    const [model, setModel] = useState("")
    const [description, setDescription] = useState("")
    const [wear, setWear] = useState(0)
    const [show, setShow] = useState(false)
    const navigate = useNavigate()

    const handleAddMaterial = async () => {
        if (brand === "" || model === "" || description === "" || wear === "") {
            setShow(true)
        }
        else {
            const data = await createMaterial(brand, model, description, wear, JSON.parse(localStorage.getItem("userAuth")).id)
            if (data.error) {
                console.log("Error")
            }
            else {
                navigate('/listmaterials')
            }
        }
    }

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    } 

    const handleHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    return (
        <>
            <NavigationBar />
            <div className='d-flex align-items-center justify-content-center' style={{marginTop: '5%'}}> 
                <div className='container-createmat d-grid d-flex align-items-center'>
                    <h1 className="custom-h1">Añadir material</h1>
                    <div className='container-createmat d-grid d-flex align-items-center' style={{marginTop: '50px',}}>
                        <label className='custom-label-createmat' htmlFor="brand">MARCA</label>
                        <input name='brand' value={brand} onChange={updateValue(setBrand)} className='custom-input-register' ></input>
                        <label className='custom-label-createmat' htmlFor="model">MODELO</label>
                        <input name='model' value={model} onChange={updateValue(setModel)} className='custom-input-register'></input>
                        <label className='custom-label-createmat' htmlFor="description">DESCRIPCIÓN</label>
                        <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-input-register'/>
                        <label className='custom-label-createmat' htmlFor="wear">KILOMETRAJE {'(KM)'}</label>
                        <input name='wear' value={wear} type={"number"} onChange={updateValue(setWear)} className='custom-input-createmat'></input>
                        <Button variant='primary' size='lg' className='mt-5 custom-button-createmat' onClick={handleAddMaterial}>AÑADIR</Button>
                    </div>
                </div>
            </div>
            <PopUp error={"Se requiere enviar todos los parámetros"} show={show} onHide={handleHide} title={"Error al crear material"}/>
        </>
    )
}

export default CreateMaterial;