package com.viglet.shiohara.api.post.type;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.viglet.shiohara.persistence.model.ShPost;
import com.viglet.shiohara.persistence.model.ShPostAttr;
import com.viglet.shiohara.persistence.model.ShPostType;
import com.viglet.shiohara.persistence.model.ShPostTypeAttr;
import com.viglet.shiohara.persistence.service.ShPostTypeAttrService;
import com.viglet.shiohara.persistence.service.ShPostTypeService;

@Path("/post/type")
public class ShPostTypeAPI {
	ShPostTypeService shPostTypeService = new ShPostTypeService();
	ShPostTypeAttrService shPostTypeAttrService = new ShPostTypeAttrService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShPostType> list() throws Exception {
		return shPostTypeService.listAll();
	}

	@Path("/{postTypeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ShPostType edit(@PathParam("postTypeId") int id) throws Exception {
		return shPostTypeService.get(id);
	}
	
	@Path("/{postTypeId}/post/model")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ShPost postStructure(@PathParam("postTypeId") int id) throws Exception {
		ShPost shPost = new ShPost();
		shPost.setShPostType(shPostTypeService.get(id));
		List<ShPostAttr> shPostAttrs = new ArrayList<ShPostAttr>();
		for (ShPostTypeAttr shPostTypeAttr : shPost.getShPostType().getShPostTypeAttrs()) {
			ShPostAttr shPostAttr = new ShPostAttr();
			shPostAttr.setShPostType(shPost.getShPostType());
			shPostAttr.setShPostTypeAttr(shPostTypeAttr);
			shPostAttr.setShPostTypeAttrId(shPostTypeAttr.getId());
			shPostAttrs.add(shPostAttr);
		}
		shPost.setShPostAttrs(shPostAttrs);
		return shPost;
		
	}

	@Path("/{postTypeId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ShPostType update(@PathParam("postTypeId") int id, ShPostType shPostType) throws Exception {
		ShPostType shPostTypeEdit = shPostTypeService.get(id);
		shPostTypeEdit.setDate(shPostType.getDate());
		shPostTypeEdit.setTitle(shPostType.getTitle());
		shPostTypeEdit.setDescription(shPostType.getDescription());
		shPostTypeEdit.setName(shPostType.getName());
		
		for (ShPostTypeAttr shPostTypeAttr : shPostType.getShPostTypeAttrs()) {
			shPostTypeAttrService.save(shPostTypeAttr);
		}
		shPostTypeService.save(shPostTypeEdit);
		return shPostTypeEdit;
	}

	@Path("/{postTypeId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("postTypeId") int id) throws Exception {
		return shPostTypeService.delete(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(ShPostType shPostType) throws Exception {
		shPostTypeService.save(shPostType);
		String result = "PostType saved : " + shPostType;
		return Response.status(200).entity(result).build();

	}

	@POST
	@Path("/{postTypeId}/attr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("postTypeId") int id, ShPostTypeAttr shPostTypeAttr) throws Exception {
		ShPostType shPostType = shPostTypeService.get(id);
		if (shPostType != null) {
			
			shPostTypeAttr.setShPostType(shPostType);
			shPostTypeAttrService.save(shPostTypeAttr);
			String result = "Post Attrib saved: " + shPostTypeAttr;
			return Response.status(200).entity(result).build();
		} else {
			return Response.status(500).entity("invalid Post Type Id").build();
		}

	}

}