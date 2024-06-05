import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {instance} from '../../api/config'

const initialState = {
    fundraisings: [],
    fundraisingsFetchStatus: 'pending',
    currentFundraising: null,
    currentFundraisingFetchStatus: 'pending',
    createStatus: 'pending',
}


export const fetchFundraisings = createAsyncThunk(
    "fundraisings/fetchFundraisings",
    async (userId) => {
        const response = await instance.get(`fundraising/user/${userId}`)
        return await response.data;
    }
)

export const fetchOneFundraising = createAsyncThunk(
    "fundraisings/fetchOneFundraising",
    async (id) => {
        const response = await instance.get(`fundraising/${id}`)
        return await response.data;
    }
)

export const createFundraising = createAsyncThunk(
    "fundraising/createFundraising",
    async (data) => {
        await instance.post(`fundraising`, JSON.stringify(data));
        return data;
    }
)

export const donateFunds = createAsyncThunk(
    "fundraising/donateFunds",
    async ({amount, id}) => {
         await instance.post(`fundraising/donate/${id}?amount=${amount}`);
        return {amount, id}
    }
)

export const closeFundraising = createAsyncThunk(
    "fundraising/closeFundraising",
    async ({id}) => {
        await instance.post(`fundraising/close/${id}`);
        return id;
    }
)

export const reportFundraising = createAsyncThunk(
    "fundraising/report",
    async ({data, id}) => {
        const resp = await instance.post(`fundraising/report`,data,{
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        })
        const dataResp = await resp.data;
        return {data: dataResp, id: id};
    }
)

const VolunteerSlice = createSlice({
    name: "volunteer",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchFundraisings.pending, (state) => {
                state.fundraisingsFetchStatus = 'pending'
            })
            .addCase(fetchFundraisings.fulfilled, (state, {payload}) => {
                state.fundraisings = payload;
                state.fundraisingsFetchStatus = 'idle'
            })
            .addCase(fetchFundraisings.rejected, (state) => {
                state.fundraisingsFetchStatus = 'error'
            })
            .addCase(fetchOneFundraising.pending, (state) => {
                state.currentFundraisingFetchStatus = 'pending'
            })
            .addCase(fetchOneFundraising.fulfilled, (state, {payload}) => {
                state.currentFundraising = payload;
                state.currentFundraisingFetchStatus = 'idle'
            })
            .addCase(fetchOneFundraising.rejected, (state) => {
                state.currentFundraisingFetchStatus = 'error'
            })
            .addCase(createFundraising.fulfilled, (state, {payload}) => {
                state.fundraisings = [...state.fundraisings, payload];
                state.createStatus = 'idle'
            })
            .addCase(donateFunds.fulfilled, (state, {payload}) => {
                state.fundraisings.find(f => f.id === payload.id).actualAmount += parseInt(payload.amount);
            })
            .addCase(closeFundraising.fulfilled, (state,{payload}) => {
                state.fundraisings.find(f => f.id === payload).closed = true;
            })
            .addCase(reportFundraising.fulfilled, (state,{payload}) => {
                state.fundraisings.find(f => f.id === payload.id).fundraisingReport = payload.data;
                state.fundraisings.find(f => f.id === payload.id).closed = true;
            })

    }
})

export default VolunteerSlice.reducer
