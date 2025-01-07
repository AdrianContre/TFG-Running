import { useState, useEffect } from "react";
import {useLocation, useNavigate } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import { Spinner, Button } from "react-bootstrap";
import PopUp from "../../auth/components/PopUp";
import { editGroup, getAllUsers } from "../services/groupService";
import Select from "react-select";

function EditGroup() {
    const location = useLocation()
    const [id, setId] = useState(null)
    const [name, setName] = useState(null)
    const [description, setDescription] = useState(null)
    const [groupMembers, setGroupMembers] = useState(null)
    const {groupId, groupName, groupDescription, members} = location.state
    const [show, setShow] = useState(false)
    const [error, setError] = useState(null)
    const [selectedUsers, setSelectedUsers] = useState(null);
    const [searchInput, setSearchInput] = useState(""); 
    const [userOptions, setUserOptions] = useState([]);
    const [filteredOptions, setFilteredOptions] = useState([]);
    const navigate = useNavigate()

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    useEffect(()=> {
        const getInfo = async () => {
            const users = await getAllUsers();
            const options = users.data.map((user) => ({
                value: user.id,
                label: user.username,
            }));
            setUserOptions(options);
            setId(groupId)
            setName(groupName)
            setDescription(groupDescription)
            setGroupMembers(members)
            const selected_options = members.map((user) => ({
                value: user.id,
                label: user.username,
            }));
            setSelectedUsers(selected_options)
        }
        getInfo()
    },[])

    const handleInputChange = (inputValue) => {
        setSearchInput(inputValue);
    
        if (inputValue) {
            const filtered = userOptions.filter((option) => {
                const regex = new RegExp(`^${inputValue.toLowerCase()}`);
                return regex.test(option.label.toLowerCase());
            });
            setFilteredOptions(filtered);
        } else {
            setFilteredOptions([]);
        }
    };

    const handleSend = async (event) => {
        event.preventDefault()
        if (selectedUsers.length == 0) {
            setError("Tiene que haber minimo un usuario en el grupo")
            setShow(true)
        } 
        else if (name === "" || description === "") {
            setError("Todos los campos son obligatorios")
            setShow(true)
        }
        let membersId = []
        selectedUsers.map(user =>{
            membersId.push(user.value)
        })
        const editgroup = await editGroup(id, name, description, membersId);
        if (editgroup.data) {
            navigate('/viewgroup',{ state: {groupId: id}})
        }
    }

    if (id === null ||name === null|| description === null|| groupMembers === null || selectedUsers === null) {
        console.log(description)
        return (<div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                    <Spinner animation="border" role="status"/>
                </div>)
    }
    return (
        <>
            <NavigationBar/>
            <h1 className="custom-h1">Editar grupo:</h1>
            <div className="create-group-container">
                <div className="form-container-create-group">
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Nombre:</label>
                        <input type="text" className="custom-input-create-group" value={name} onChange={(e) => setName(e.target.value)}/>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-group'>Descripción:</label>
                        <textarea className='custom-textarea-create-group' value={description} onChange={(e) => setDescription(e.target.value)} />
                    </div>
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Añadir usuarios:</label>
                        <Select
                            isMulti
                            options={filteredOptions}
                            value={selectedUsers}
                            onChange={(selected) => setSelectedUsers(selected)}
                            onInputChange={handleInputChange} 
                            placeholder="Escribe para buscar usuarios..."
                            className="custom-select-creategroup"
                            noOptionsMessage={() =>
                                searchInput
                                    ? "No hay resultados"
                                    : "Escribe para buscar usuarios"
                            }
                        />
                    </div>
                </div>
            </div>
            <div style={{ display: "flex", justifyContent: "center" }}>
                <Button variant="primary" size="lg" className="mt-5" onClick={handleSend}>
                    CONFIRMAR EDICIÓN
                </Button>
            </div>
            <PopUp error={error} show={show} onHide={onHide} title={"Error al editar grupo"}/>
        </>
    )
}

export default EditGroup;