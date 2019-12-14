package com.santhos.truelocker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.santhos.truelocker.R;

import java.util.List;

import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceListViewHolder>  {

    private List<NetworkServiceDiscoveryInfo> serviceList;
    private Context context ;
    private OnItemClickListner listener;
    public ServiceListAdapter(List<NetworkServiceDiscoveryInfo> data, Context context , OnItemClickListner listener) {
        this.serviceList = data;
        this.context=context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ServiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_list_item, parent, false);

        return new ServiceListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListViewHolder holder, int position) {
        NetworkServiceDiscoveryInfo networkServiceDiscoveryInfo = serviceList.get(position);
        holder.serviceName.setText(networkServiceDiscoveryInfo.getServiceName().replace("\\"," "));
        holder.ipAddress.setText(networkServiceDiscoveryInfo.getAddress() + ":"+networkServiceDiscoveryInfo.getServicePort());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick("http:/"+networkServiceDiscoveryInfo.getAddress() + ":"+networkServiceDiscoveryInfo.getServicePort());
            }
        });
    }



    @Override
    public int getItemCount() {
        return serviceList.size();
    }



    public class ServiceListViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName;
        TextView ipAddress;
        LinearLayout linearLayout;
        public ServiceListViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            ipAddress = itemView.findViewById(R.id.ipAddress);
            linearLayout = itemView.findViewById(R.id.service);
        }
    }

    public interface OnItemClickListner {
void onItemClick(String url);
    }


}
