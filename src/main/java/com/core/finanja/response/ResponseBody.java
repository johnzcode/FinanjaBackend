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

    public void responseActive() {
        this.totalCorrect = 0;
        this.totalDefaults = 0;
        this.totalProcess = 0;
        this.detailCorrect = new ArrayList<>();
        this.detailDefaults = new ArrayList<>();
    }

    public void addFailed(DetailBody detail) {
        this.detailDefaults.add(detail);
        this.totalDefaults = this.detailDefaults.size();
        this.totalProcess = this.totalDefaults + this.totalCorrect;
    }

    public DetailBody createErrorDetail(String input, String message, int index) {
        ArrayList<ErrorBody> listError = new ArrayList<>();
        ErrorBody errorBody = new ErrorBody();

        errorBody.setInput(input);
        errorBody.setMsg(message);
        listError.add(errorBody);

        DetailBody detailBody = new DetailBody();
        detailBody.setIndex(index);
        detailBody.setSuccess(false);
        detailBody.setError(listError);

        return detailBody;
    }

    public void addFailed(DetailBody detail, Exception ex) {
        ArrayList<ErrorBody> listError = new ArrayList<>();
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
    }

    public void addCorrect(DetailBody detail) {
        detail.setSuccess(true);
        this.detailCorrect.add(detail);
        this.totalCorrect = this.detailCorrect.size();
        this.totalProcess = this.totalDefaults + this.totalCorrect;
    }
}
