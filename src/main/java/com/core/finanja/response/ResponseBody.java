package com.core.finanja.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class ResponseBody {
    private String result;

    private Integer totalDefaults;

    private Integer totalCorrect;

    private ArrayList<DetailBody> detailDefaults;

    private ArrayList<DetailBody> detailCorrect;

    private Integer totalProcess;

    public String getResult(){
        return !detailDefaults.isEmpty() ? "success with errors" : "success";
    }

    public void responseActive() throws Exception{
        try {
            this.totalCorrect = 0;
            this.totalDefaults = 0;
            this.totalProcess = 0;
            this.detailCorrect = new ArrayList<DetailBody>();
            this.detailDefaults = new ArrayList<DetailBody>();
        } catch (Exception e){
            throw e;
        }
    }

    public void addFailed(DetailBody detail) throws Exception{
        try{
            this.detailDefaults.add(detail);
            this.totalDefaults = this.detailDefaults.size();
            this.totalProcess = this.totalDefaults + this.totalCorrect;
        }catch (Exception e){
            throw e;
        }
    }

    public void addFailed(DetailBody detail, Exception ex) throws Exception{
        try {
            ArrayList<ErrorBody> listError = new ArrayList<ErrorBody>();
            ErrorBody error = new ErrorBody();

            if (ex.getCause() == null && ex.getMessage() != null){
                error.setMsg(ex.getMessage());
            }else {
                error.setMsg("Error interno API");
            }

            listError.add(error);
            detail.setError(listError);
            detail.setSuccess(false);

            this.detailDefaults.add(detail);
            this.totalDefaults = this.detailDefaults.size();
            this.totalProcess = this.totalDefaults + this.totalCorrect;
        } catch (Exception e){
            throw e;
        }
    }

    public void addCorrect(DetailBody detail) throws Exception{
        try{
            detail.setSuccess(true);
            this.detailCorrect.add(detail);
            this.totalCorrect = this.detailCorrect.size();
            this.totalProcess = this.totalDefaults + this.totalCorrect;
        }catch (Exception e){
            throw e;
        }
    }
}
