import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import {editResult, getRunningResult } from "../services/activitiesService";
import Select from "react-select";
import { getUserMaterials } from "../../profile/services/materialService";
import Button from 'react-bootstrap/Button';
import PopUp from "../../auth/components/PopUp";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale} from  "react-datepicker";
import { es } from 'date-fns/locale/es';
import RatingComponent from "../../TrainingPlans/components/RatingComponent";
import { uploadRouteToResult } from "../../TrainingPlans/services/trainingResultService";
import { Spinner } from "react-bootstrap";
import { useSelector } from "react-redux";

function EditRunningResult() {
    registerLocale('es', es)
    const navigate = useNavigate()
    const location = useLocation();
    const { sesionId } = location.state;
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [distance, setDistance] = useState(null);
    const [duration, setDuration] = useState("");
    const [fcAvg, setFcAvg] = useState(null);
    const [pace, setPace] = useState(null);
    const [route, setRoute] = useState(null);
    const [materials, setMaterials] = useState([]);
    const [selectedMaterials, setSelectedMaterials] = useState([]);
    const [materialOptions, setMaterialOptions] = useState([])
    const [defaultMats, setDefault] = useState([])
    const [rating, setRating] = useState(null);
    const [show,setShow] = useState(false)
    const [error, setError] = useState("")
    const [title, setTitle] = useState("")
    const [date,setDate] = useState(new Date())
    const [activity, setActivity] = useState(null)
    const userId = useSelector((state) => { return state.auth.user.id})

    useEffect(() => {
        const fetchInfo = async () => {
            console.log(sesionId)
            const runningResult = await getRunningResult(sesionId);
            setName(runningResult.data.name);
            setDescription(runningResult.data.description);
            setDistance(runningResult.data.distance);
            setDuration(runningResult.data.duration);
            setFcAvg(runningResult.data.fcAvg);
            setPace(runningResult.data.pace.toFixed(2));
            setDate(new Date(runningResult.data.date))
            setRating(runningResult.data.effort)

            const activityMaterialLabels = runningResult.data.materials;
            setDefault(activityMaterialLabels)

            const mats = await getUserMaterials(userId);
            setMaterials(mats);
            const options = mats.map(material => ({
                value: material.id, 
                label: `${material.brand} ${material.model}`
            }))
            setMaterialOptions(options)
            let selected = []
            activityMaterialLabels.forEach(material => {
                options.forEach(opt => {
                    if (opt.label === material) {
                        selected.push(opt)
                    }
                })
            })
            setSelectedMaterials(selected); 
            setActivity(runningResult.data)
        };


        fetchInfo();
    }, []);

    const handleRatingChange = (value) => {
        setRating(value);
    };

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    };

    const handleHide = (event) => {
        event.preventDefault();
        setShow(false);
    };

    const handleEditRunningResult = async (event) => {
        event.preventDefault()
        let materialsId = []
        selectedMaterials.forEach((material) => {
            materialsId.push(material.value)
        })
        console.log(materialsId)
        const dataInfo = date.toISOString() 
        if (name == "" || description == "" || distance == "" || duration == "" || pace == "" || fcAvg == "" || materialsId.length == 0 || date == "" || rating == null) {
            setShow(true)
            setError("Todos los campos excepto la ruta son obligatorios")
            setTitle("Error al crear actividad")
        }
        else {
            try {
                const activity = await editResult(sesionId, "RunningResult", description, rating, dataInfo, materialsId, distance, duration, pace, fcAvg)
                if (activity.data) {
                    if (route !== null) {
                        console.log("entro aqui")
                        const formData = new FormData()
                        formData.append('route', route)
                        const uploadRoute = await uploadRouteToResult(formData,sesionId)
                    }
                    navigate('/activities')
                }
            }
            catch (Error) {
                console.log(Error)
                setShow(true)
                setError("Revisa el formato de los campos del formulario")
                setTitle("Error al crear actividad")
            }
            
        }

    }

    if (!activity) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    return (
        <>
            <NavigationBar />
            <div className='d-flex align-items-center justify-content-center' style={{ marginTop: '5%' }}> 
                <div className='container-createact d-flex'>
                    <h1 className="custom-h1">Editar resultado</h1>
                    <div className='form-columns'>
                        <div className='form-column'>
                            <label className='custom-label-createact' htmlFor="brand">NOMBRE</label>
                            <input name='brand' value={name} className='custom-input-createact' disabled/>
                            
                            <label className='custom-label-createact' htmlFor="description">DESCRIPCIÓN</label>
                            <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-textarea-createact' />

                            <label className='custom-label-createact'>COMO TE SIENTES?</label>
                            <RatingComponent onChange={handleRatingChange} />

                            <label className='custom-label-createact' htmlFor="distance">DISTANCIA {'(km)'}</label>
                            <input name='distance' type="number" step="0.001" value={distance} onChange={updateValue(setDistance)} className='custom-input-createact' />

                            <label className='custom-label-createact' htmlFor="duration">DURACIÓN {'(hh:mm:ss)'}</label>
                            <input name='duration' value={duration} onChange={updateValue(setDuration)} className='custom-input-createact' />
                        </div>
                        <div className='form-column'>
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
                                value={selectedMaterials}
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
                    <Button variant='primary' size='lg' className='mt-5 custom-button-createact' onClick={handleEditRunningResult}>EDITAR</Button>
                </div>
            </div>
            <PopUp error={error} show={show} onHide={handleHide} title={title}/>
        </>
    );
}

export default EditRunningResult;