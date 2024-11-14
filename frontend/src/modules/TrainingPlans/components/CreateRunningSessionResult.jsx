import React from "react";
import NavigationBar from "../../home/components/NavigationBar";
import { getUserMaterials } from "../../profile/services/materialService";
import { useEffect, useState } from "react";
import PopUp from "../../auth/components/PopUp";
import Button from 'react-bootstrap/Button';
import '../../activities/styles/createActivity.css'
import Select from "react-select";
import { addRoute, createManualActivity } from '../../activities/services/activitiesService';
import { useLocation, useNavigate } from "react-router";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale, setDefaultLocale } from  "react-datepicker";
import { es } from 'date-fns/locale/es';
import RatingComponent from "./RatingComponent";
import { createRunningSessionResult, uploadRouteToResult } from "../services/trainingResultService";

function CreateRunningSessionResult () {

    registerLocale('es', es)
    const location = useLocation()
    const {plan, session} = location.state
    const [materials, setMaterials] = useState([]);
    const [show, setShow] = useState(false);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [distance, setDistance] = useState(0);
    const [duration, setDuration] = useState("00:00:00");
    const [pace, setPace] = useState(0);
    const [fcAvg, setFcAvg] = useState(0);
    const [route, setRoute] = useState(null);
    const [selectedMaterials, setSelectedMaterials] = useState([]);
    const [error, setError] = useState("")
    const [title,setTitle] = useState("")
    const navigate = useNavigate()

    const [date, setDate] = useState(new Date());

    const [rating, setRating] = useState(null);

    const handleRatingChange = (value) => {
        setRating(value);
    };

    useEffect(() => {
        const getMaterials = async () => {
            const runnerId = JSON.parse(localStorage.getItem("userAuth")).id;
            const userMaterials = await getUserMaterials(runnerId);
            if (userMaterials.error) {
                setShow(true);
            } else { 
                setMaterials(userMaterials);
            }
            setName(session.name)
        };
        getMaterials();
    }, []);

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    };

    const handleHide = (event) => {
        event.preventDefault();
        setShow(false);
    };

    const materialOptions = materials.map(material => ({
        value: material.id, 
        label: `${material.brand} ${material.model}`
    }));

    const handleSendActivity = async (event) => {
        event.preventDefault();
        const runnerId = JSON.parse(localStorage.getItem("userAuth")).id
        let materialsId = []
        selectedMaterials.forEach((material) => {
            materialsId.push(material.value)
        })
        const dataInfo = date.toISOString()
        if (description == "" || distance == "" || duration == "" || pace == "" || fcAvg == "" || materialsId.length == 0 || date == "" || rating == null) {
            setShow(true)
            setError("Todos los campos excepto la ruta son obligatorios")
            setTitle("Error al crear actividad")
        }
        // if (false) { 
        //     console.log("hola")
        // }
        else {
            try {
                const activity = await createRunningSessionResult(plan.id, runnerId, session.id, description, rating, distance, duration, pace, fcAvg, materialsId, dataInfo)
                if (activity.data) {
                    if (route !== null) {
                        const id = activity.data
                        const formData = new FormData()
                        formData.append('route', route)
                        const uploadRoute = await uploadRouteToResult(formData,id)
                    }
                    navigate('/viewplan', { state: {planId: plan.id}})
                }
            }
            catch (Error) {
                setShow(true)
                setError("Revisa el formato de los campos del formulario")
                setTitle("Error al crear actividad")
            }
            
        }
        
    };

    return (
        <>
            <NavigationBar />
                <div className='d-flex align-items-center justify-content-center' style={{ marginTop: '5%' }}> 
                    <div className='container-createact d-flex'>
                        <h1 className="custom-h1">Añadir resultado sesión de carrera</h1>
                        <div className='form-columns'>
                            <div className='form-column'>
                                <label className='custom-label-createact' htmlFor="brand">PLAN ASOCIADO</label>
                                <input name='brand' value={plan.name} onChange={updateValue(setName)} className='custom-input-createact' disabled/>

                                <label className='custom-label-createact' htmlFor="brand">NOMBRE</label>
                                <input name='brand' value={name} onChange={updateValue(setName)} className='custom-input-createact' disabled/>
                                
                                <label className='custom-label-createact' htmlFor="description">DESCRIPCIÓN</label>
                                <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-textarea-createact' />

                                <label className='custom-label-createact'>COMO TE SIENTES?</label>
                                <RatingComponent onChange={handleRatingChange} />

                                <label className='custom-label-createact' htmlFor="distance">DISTANCIA {'(km)'}</label>
                                <input name='distance' type="number" step="0.001" value={distance} onChange={updateValue(setDistance)} className='custom-input-createact' />
                            </div>
                            <div className='form-column'>
                                <label className='custom-label-createact' htmlFor="duration">DURACIÓN {'(hh:mm:ss)'}</label>
                                <input name='duration' value={duration} onChange={updateValue(setDuration)} className='custom-input-createact' />

                                <label className='custom-label-createact' htmlFor="pace">RITMO {'(min/km, ej. 5.30)'}</label>
                                <input name='pace' type="number" step='0.01' value={pace} onChange={updateValue(setPace)} className='custom-input-createact' />

                                <label className='custom-label-createact' htmlFor="fcAvg">FRECUENCIA CARDIACA MEDIA {'(ppm)'}</label>
                                <input name='fcAvg' type="number" value={fcAvg} onChange={updateValue(setFcAvg)} className='custom-input-createact' />

                                <label className='custom-label-createact' htmlFor="route">RUTA {'(opcional, .gpx)'}</label>
                                <input name='route' type="file" onChange={(e) => setRoute(e.target.files[0])} accept=".gpx" className='custom-input-createact' />

                                <label className='custom-label-createact' htmlFor="materials">MATERIALES</label>
                                <Select
                                    isMulti
                                    options={materialOptions}
                                    onChange={(selected) => setSelectedMaterials(selected)}
                                    className="custom-select-createact"
                                />
                                <div className="row" style={{marginTop: '20px'}}>
                                    <label className='custom-label-createact' htmlFor="date">FECHA</label>
                                    <DatePicker 
                                        name="date"
                                        selected={date} 
                                        onChange={(date) => setDate(date)} 
                                        dateFormat="dd/MM/yyyy"
                                        className='custom-input-createact'
                                        locale="es"
                                    />
                                </div>
                                
                            </div>
                        </div>
                        <Button variant='primary' size='lg' className='mt-5 custom-button-createact' onClick={handleSendActivity}>AÑADIR</Button>
                    </div>
                </div>
                <PopUp error={error} show={show} onHide={handleHide} title={title}/>
        </>
    )
}

export default CreateRunningSessionResult;