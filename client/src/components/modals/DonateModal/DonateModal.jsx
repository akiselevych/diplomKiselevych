import {useState} from 'react';

import styles from '../VerifyModal/styles.module.scss'
import s from "../RegisterConfirmModal/RegisterConfirmModal.module.scss";
import {Modal} from "@mui/material";
import {useForm} from "react-hook-form";
import {donateFunds} from "../../../store/slices/Volunteer.slice.jsx";
import {useDispatch} from "react-redux";

const DonateModal = ({open, onClose, fundId}) => {

    const {register,handleSubmit,formState: {isValid}} = useForm();

    const dispatch = useDispatch();

    const onSubmit = (data) => {
        dispatch(donateFunds({amount: data.sum, id: fundId}))
        onClose();
    }

    return (
        <Modal open={open} onClose={onClose} className={s.modalInner}
               disableAutoFocus>
            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                <div className={styles.fields}>
                    <div className={styles.inputField}>
                        <label className={styles.title}>Введіть суму яку хочете задонатити:</label>
                        <input type="number" {...register("sum", {required: true, min: 1})} min={1}/>
                    </div>
                </div>
                <div className={styles.btns}>
                    <button type={"submit"} className={styles.submitBtn} disabled={!isValid}>Задонатити</button>
                    <button type={"button"} onClick={onClose} className={styles.submitBtn}>Скасувати</button>
                </div>
            </form>
        </Modal>
    );
};

export default DonateModal;
